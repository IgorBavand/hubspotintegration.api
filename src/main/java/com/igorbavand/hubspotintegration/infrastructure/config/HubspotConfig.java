package com.igorbavand.hubspotintegration.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HubspotConfig {

    @Value("${hubspot.client.id}")
    private String clientId;

    @Value("${hubspot.client.secret}")
    private String clientSecret;

    @Value("${hubspot.redirect.uri}")
    private String redirectUri;

    @Value("${hubspot.token.url}")
    private String tokenUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://api.hubapi.com")
                .build();
    }
} 