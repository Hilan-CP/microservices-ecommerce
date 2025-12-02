package com.hdev.gateway.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

//    @Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("eureka-server",
//                        route -> route.path("/eureka/main")
//                                .filters(filter -> filter.rewritePath("/eureka/main", "/"))
//                                .uri("http://localhost:8761"))
//                .route("eureka-server-static-resources",
//                        route -> route.path("/eureka/**")
//                                .uri("http://localhost:8761"))
//                .build();
//    }
}
