package com.hdev.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(authorize -> authorize.anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                        .jwtAuthenticationConverter(roleConverter())))
                .build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> roleConverter(){
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(token -> {
            List<String> roles = token.getClaimAsMap("resource_access")
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals("authorization-pkce"))
                    .flatMap(entry -> {
                        Map<String, List<String>> clientProperties = (Map<String, List<String>>) entry.getValue();
                        return clientProperties.get("roles").stream();
                    })
                    .toList();
            return Flux.fromIterable(roles).map(role -> new SimpleGrantedAuthority("ROLE_" + role));
        });
        return converter;
    }
}
