package com.payment.system_payment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(Collections.singletonList(new Server()
                        .url("https://system-payment.com")
                        .description("System Payment API")))
                .info(new Info().title("Payment API"));
    }
}
