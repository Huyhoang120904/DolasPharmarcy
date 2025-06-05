package com.hoangHocDev.Dolas_Pharmarcy.configuration;

import com.hoangHocDev.Dolas_Pharmarcy.dto.request.IntrospectTokenRequest;
import com.hoangHocDev.Dolas_Pharmarcy.dto.response.IntrospectTokenResponse;
import com.hoangHocDev.Dolas_Pharmarcy.service.AuthenticationService;
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
public class CustomJwtDecoder implements JwtDecoder {


    @Value("${jwt.signerKey}")
    @NonFinal
    private String signerKey;

    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder decoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            IntrospectTokenRequest request = IntrospectTokenRequest.builder().token(token).build();
            IntrospectTokenResponse response = authenticationService.introspect(request);

            if (!response.isValid()) {
                throw new JwtException("Token invalid");
            }

            log.info("{}", response);

        } catch (JwtException e) {
            e.printStackTrace();
        }

        if (Objects.isNull(decoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            decoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
        }

        return decoder.decode(token);
    }
}
