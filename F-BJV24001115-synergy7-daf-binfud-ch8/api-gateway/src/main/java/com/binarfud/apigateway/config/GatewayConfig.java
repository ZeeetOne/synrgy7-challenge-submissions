package com.binarfud.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("Binarfud", r -> r.path("/binarfud/**")
                        .uri("http://localhost:8081")
                )
                .route("Notification", r -> r.path("/notification/**")
                        .uri("http://localhost:8082")
                )
                .build();
    }
}
