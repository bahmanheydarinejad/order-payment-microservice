package com.example.order.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    GroupedOpenApi api() {
        return GroupedOpenApi.builder().group("order").pathsToMatch("/**").build();
    }

    @Bean
    OpenAPI meta() {
        return new OpenAPI().info(new Info().title("Order API").version("v1"));
    }
}
