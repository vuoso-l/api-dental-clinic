package com.example.dentalClinicApi.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        description = "Insert JWT Token obtained from user. Authorization URL: https://api-dental-clinic.herokuapp.com/auth/login",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Dentist clinic API")
                        .description("This document contains the documentation for using the dental clinic API. Base url: https://api-dental-clinic.herokuapp.com/")
                        .contact(new Contact()
                                .name("Lucas Vuoso")
                                .email("vuosolucas@gmail.com")
                                .url("https://portfolio-vuoso-lucas.vercel.app/")
                        )
                        .version("1.0.0")
                );
    }
}
