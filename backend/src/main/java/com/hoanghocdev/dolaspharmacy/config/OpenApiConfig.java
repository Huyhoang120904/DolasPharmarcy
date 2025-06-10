package com.hoanghocdev.dolaspharmacy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nguyễn Huy Hoàng",
                        email = "hoangbeo1124@gmail.com",
                        url = "https://github.com/Huyhoang120904"
                ),
                description = "OpenApi documentation for Dolas Pharmarcy",
                title =  "Dolas Pharmarcy Backend Service",
                version = "1.0"
//                ,
//                license = @License(
//                        name = "Placeholder",
//                        url = "google.com"
//                )
        ),
        servers = {
                @Server (
                        description = "Local env",
                        url = "http://localhost:8080/api/v1"
                ),
                @Server (
                        description = "Test env",
                        url = "http://localhost:8080/api/v1"
                )
        },
        security = @SecurityRequirement(name = "bearerAuth")
)

@SecurityScheme(
        name = "bearerAuth",
        description = "Jwt Auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {

}
