package com.jpmc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("collateral-service", r -> r.path("/api/v1/collaterals/**")
                        .uri("lb://collateral-service"))
                .route("position-service", r -> r.path("/api/v1/positions/**")
                        .uri("lb://position-service"))
                .route("price-service", r -> r.path("/api/v1/prices/**")
                        .uri("lb://price-service"))
                .route("eligibility-service", r -> r.path("/api/v1/eligibilities/**")
                        .uri("lb://eligibility-service"))
                .build();
    }

}
