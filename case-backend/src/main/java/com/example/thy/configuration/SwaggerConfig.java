package com.example.thy.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${app.swagger.title}")
    private String title;
    @Value("${app.swagger.description}")
    private String description;
    @Value("${app.swagger.version}")
    private String version;
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version) // ✅ OpenAPI versiyonu tanımlandı
                        .description(description));
    }
}