package com.example.gatewayserver.filters.factory;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class SampleGatewayFilterFactory extends AbstractGatewayFilterFactory<SampleGatewayFilterFactory.Configuration> {

    private final Logger logger = LoggerFactory.getLogger(SampleGatewayFilterFactory.class);

    public SampleGatewayFilterFactory() {
        super(Configuration.class);
    }

    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {
            logger.info("Executing pre gateway filter factory: " + config.message);

            return chain.filter(exchange).then(Mono.fromRunnable( () -> {
                Optional.ofNullable(config.cookieValue)
                        .ifPresent(cookie -> {
                            exchange.getResponse()
                                    .addCookie(
                                            ResponseCookie.from(config.cookieName, cookie).build()
                                    );
                        });
                logger.info("Executing post gateway filter factory: " + config.message);
            }));
        };
    }

    @Data
    public static class Configuration{
        private String message;
        private String cookieValue;
        private String cookieName;
    }
}
