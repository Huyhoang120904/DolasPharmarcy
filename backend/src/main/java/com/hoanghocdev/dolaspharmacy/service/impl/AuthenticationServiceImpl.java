package com.hoanghocdev.dolaspharmacy.service.impl;

import com.hoanghocdev.dolaspharmacy.dto.request.AuthenticationRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.IntrospectTokenRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.TokenRequest;
import com.hoanghocdev.dolaspharmacy.dto.request.UserCreationRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.AuthenticationResponse;
import com.hoanghocdev.dolaspharmacy.dto.response.IntrospectTokenResponse;
import com.hoanghocdev.dolaspharmacy.entity.InvalidToken;
import com.hoanghocdev.dolaspharmacy.entity.Permission;
import com.hoanghocdev.dolaspharmacy.entity.UserEntity;
import com.hoanghocdev.dolaspharmacy.exception.AppException;
import com.hoanghocdev.dolaspharmacy.exception.ErrorCode;
import com.hoanghocdev.dolaspharmacy.repository.InvalidTokenRepository;
import com.hoanghocdev.dolaspharmacy.repository.UserEntityRepository;
import com.hoanghocdev.dolaspharmacy.service.AuthenticationService;
import com.hoanghocdev.dolaspharmacy.service.UserEntityService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    UserEntityRepository userEntityRepository;
    PasswordEncoder passwordEncoder;
    InvalidTokenRepository invalidTokenRepository;
    private final UserEntityService userEntityService;

    @Value("${jwt.signerKey}")
    @NonFinal
    String signerKey;

    @Value("${jwt.validDuration}")
    @NonFinal
    int validDaration;

    @Value("${jwt.refreshableDuration}")
    @NonFinal
    int refreshableDuration;

    @Override
    public IntrospectTokenResponse introspect(IntrospectTokenRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                log.info("Role: {}", authority.getAuthority());
            }
        }
        boolean isValid = true;
        try {
           verifyToken(request.getToken(), false);
        } catch (ParseException | JOSEException | AppException e) {
            isValid = false;
        }
        return IntrospectTokenResponse.builder().isValid(isValid).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        UserEntity userEntity = userEntityRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), userEntity.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(userEntity);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public AuthenticationResponse register(UserCreationRequest request) {
        userEntityService.createUser(request);
        return authenticate(AuthenticationRequest.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build());
    }

    @Override
    public AuthenticationResponse refresh(TokenRequest request) throws ParseException, JOSEException {

        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expireAt = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidToken invalidToken = InvalidToken.builder()
                .id(jit)
                .expireAt(expireAt)
                .build();

        invalidTokenRepository.save(invalidToken);

        String usename = signedJWT.getJWTClaimsSet().getSubject();

        UserEntity userEntity = userEntityRepository.findByUsername(usename)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        String token = generateToken(userEntity);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public void logout(TokenRequest request) {
        try {
            SignedJWT signedJWT = verifyToken(request.getToken(), true);

            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            Date expireAt = signedJWT.getJWTClaimsSet().getExpirationTime();

            InvalidToken invalidToken = InvalidToken.builder()
                    .id(jit)
                    .expireAt(expireAt)
                    .build();

            invalidTokenRepository.save(invalidToken);
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.TOKEN_DECODE_FAILED);
        }
    }

    String buildScope(UserEntity userEntity) {
        StringJoiner joiner = new StringJoiner("");

        if (!CollectionUtils.isEmpty(userEntity.getRoles())) {
            userEntity.getRoles().forEach(role -> {
                joiner.add("ROLE_" + role.getRolename());

                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().stream()
                            .map(Permission::getPermissionName)
                            .forEach(joiner::add);
                }
            });
        }
        return joiner.toString();
    }

    SignedJWT verifyToken(String token, boolean isRefresh) throws ParseException, JOSEException {

        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expireTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getExpirationTime()
                .toInstant().plus(refreshableDuration, ChronoUnit.SECONDS)
                .toEpochMilli())

                : signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean isAuthenticated = signedJWT.verify(verifier);

        if (!(isAuthenticated && expireTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);


        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        if (invalidTokenRepository.existsById(jit)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    String generateToken(UserEntity userEntity) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userEntity.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(validDaration, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(userEntity))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.TOKEN_GENERATE_FAILED);
        }
    }

}
