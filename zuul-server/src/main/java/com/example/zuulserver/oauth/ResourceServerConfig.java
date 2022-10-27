package com.example.zuulserver.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/security/oauth/**")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/products/products", "/api/items/items", "/api/users/users")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/products/products/{id}", "/api/items/items/{id}", "/api/users/users/{id}")
                .hasAnyRole("ADMIN", "USER")
                .antMatchers( "/api/products/**", "/api/items/**", "/api/users/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(acessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter acessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("_some_secret_code_");
        return tokenConverter;
    }
}