package com.hdev.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class RouteConfig {

    @Bean
    RedisRateLimiter rateLimiter(){
        return new RedisRateLimiter(1, 10, 2);
    }

    @Bean
    KeyResolver keyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("eureka-server", route -> route
                        .path("/eureka/main")
                        .filters(filter -> filter.rewritePath("/eureka/main", "/"))
                        .uri("http://localhost:8761"))
                .route("eureka-server-static-resources", route -> route
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .route("user-service", route -> route
                        .path("/users/**")
                        .filters(filter -> filter
                                .circuitBreaker(breaker -> breaker
                                        .setName("gatewayCircuitBreaker")
                                        .setFallbackUri("forward:/errors/users"))
                                .requestRateLimiter(rateLimiter -> rateLimiter
                                        .setRateLimiter(rateLimiter())
                                        .setKeyResolver(keyResolver())))
                        .uri("lb://USER-SERVICE"))
                .route("product-service", route -> route
                        .path("/products/**", "/categories/**")
                        .filters(filter -> filter
                                .circuitBreaker(breaker -> breaker
                                        .setName("gatewayCircuitBreaker")
                                        .setFallbackUri("forward:/errors/products"))
                                .requestRateLimiter(rateLimiter -> rateLimiter
                                        .setRateLimiter(rateLimiter())
                                        .setKeyResolver(keyResolver())))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("order-service", route -> route
                        .path("/orders/**", "/cart/**")
                        .filters(filter -> filter
                                .circuitBreaker(breaker -> breaker
                                        .setName("gatewayCircuitBreaker")
                                        .setFallbackUri("forward:/errors/orders"))
                                .requestRateLimiter(rateLimiter -> rateLimiter
                                        .setRateLimiter(rateLimiter())
                                        .setKeyResolver(keyResolver())))
                        .uri("lb://ORDER-SERVICE"))
                .build();
    }
}
