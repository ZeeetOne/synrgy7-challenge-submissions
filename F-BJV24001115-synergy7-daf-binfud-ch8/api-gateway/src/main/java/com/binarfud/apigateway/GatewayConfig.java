package com.binarfud.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("binarfud-service", r -> r.path("/binarfud/**")
                        .filters(f -> f.rewritePath("/binarfud(?<segment>/?.*)", "${segment}"))
                        .uri("http://localhost:8081"))
                .route("notification-service", r -> r.path("/notification/**")
                        .filters(f -> f.rewritePath("/notification(?<segment>/?.*)", "${segment}"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
