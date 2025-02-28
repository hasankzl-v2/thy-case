package com.example.thy.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("THY Case Backend")
                        .version("2.0") // ✅ OpenAPI versiyonu tanımlandı
                        .description("Spring Boot API with Swagger Documentation THY Case"));
    }
}