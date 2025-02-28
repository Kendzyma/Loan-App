package com.loanapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =
        @Info(
                title = "Loan Service API",
                version = "1.0",
                description = "APIs Loan related activities.",
                contact = @Contact(name = "Tiamiyu Kehinde", url = "nam", email = "Tiamiyu@getrova.com")))
@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI customOpenAPI() {
                SecurityScheme securityScheme = new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT");

                SecurityRequirement securityRequirement = new SecurityRequirement()
                        .addList("Bearer Auth");

                return new OpenAPI()
                        .components(new Components().addSecuritySchemes("Bearer Auth", securityScheme))
                        .addSecurityItem(securityRequirement);
        }
}
