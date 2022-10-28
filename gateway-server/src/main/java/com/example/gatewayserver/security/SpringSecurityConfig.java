package com.example.gatewayserver.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;

    public SpringSecurityConfig(JwtAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http){
        return http
                .authorizeExchange()
                .pathMatchers("/api/security/oauth/**")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/api/products/products", "/api/items/items", "/api/users/users")
                .permitAll()
                .pathMatchers(HttpMethod.GET,"/api/products/products/{id}", "/api/items/items/{id}", "/api/users/users/{id}")
                .hasAnyRole("ADMIN", "USER")
                .pathMatchers("/api/products/**", "/api/items/**", "/api/users/**")
                .hasRole("ADMIN")
                .anyExchange()
                .authenticated()
                .and()
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf()
                .disable()
                .build();
    }
}
