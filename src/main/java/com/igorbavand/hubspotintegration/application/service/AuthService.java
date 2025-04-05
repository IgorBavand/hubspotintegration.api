package com.igorbavand.hubspotintegration.application.service;

import com.igorbavand.hubspotintegration.infrastructure.client.HubspotWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final HubspotWebClient hubspotWebClient;

    @Value("${hubspot.client.id}")
    private String clientId;

    @Value("${hubspot.client.secret}")
    private String clientSecret;

    @Value("${hubspot.redirect.uri}")
    private String redirectUri;

    public String generateAuthorizationUrl() {
        log.info("Generating authorization URL for client ID: {}", clientId);

        String scope = "crm.objects.contacts.write oauth crm.objects.contacts.read";

        String encodedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        String encodedScope = URLEncoder.encode(scope, StandardCharsets.UTF_8);

        return String.format("https://app.hubspot.com/oauth/authorize?client_id=%s&redirect_uri=%s&scope=%s&response_type=code",
                clientId, encodedRedirectUri, encodedScope);
    }

    public String exchangeCodeForToken(String code) {
        log.info("Delegating code exchange to HubspotWebClient");
        return hubspotWebClient.exchangeCodeForToken(clientId, clientSecret, redirectUri, code);
    }
}
