package com.hoanghocdev.dolaspharmacy.config;

import com.hoanghocdev.dolaspharmacy.dto.request.IntrospectTokenRequest;
import com.hoanghocdev.dolaspharmacy.dto.response.IntrospectTokenResponse;
import com.hoanghocdev.dolaspharmacy.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CustomJwtDecoder implements JwtDecoder {


    @Value("${jwt.signerKey}")
    @NonFinal
    private String signerKey;

    @NonFinal
    private NimbusJwtDecoder decoder = null;

    private AuthenticationService authenticationService;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            IntrospectTokenRequest request = IntrospectTokenRequest.builder().token(token).build();
            IntrospectTokenResponse response = authenticationService.introspect(request);

            if (!response.isValid()) {
                throw new JwtException("Token invalid");
            }
        } catch (JwtException _){}

        if (Objects.isNull(decoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            decoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
        }

        return decoder.decode(token);
    }
}
