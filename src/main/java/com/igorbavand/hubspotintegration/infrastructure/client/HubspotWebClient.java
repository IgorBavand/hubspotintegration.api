package com.igorbavand.hubspotintegration.infrastructure.client;

import com.igorbavand.hubspotintegration.application.dto.response.ContactResponse;
import com.igorbavand.hubspotintegration.exception.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
public class HubspotWebClient {

    private static final String CONTACTS_URI = "/crm/v3/objects/contacts";
    private static final String TOKEN_URI = "/oauth/v1/token";

    private final WebClient webClient;

    public HubspotWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ContactResponse createContact(String accessToken, Map<String, Object> payload) {
        log.debug("Token: {}", accessToken);

        try {
            Map<String, Object> response = webClient.post()
                    .uri(CONTACTS_URI)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> {
                        HttpStatus status = HttpStatus.valueOf(clientResponse.statusCode().value());
                        return clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("No response body")
                                .flatMap(errorBody -> {
                                    log.error("HubSpot API error - Status: {}, Body: {}", status, errorBody);
                                    return Mono.error(new ExternalApiException(status, errorBody));
                                });
                    })
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            if (response != null && response.containsKey("properties")) {
                Map<String, Object> properties = (Map<String, Object>) response.get("properties");
                ContactResponse contact = new ContactResponse();
                contact.setEmail((String) properties.get("email"));
                contact.setFirstname((String) properties.get("firstname"));
                contact.setLastname((String) properties.get("lastname"));
                contact.setPhone((String) properties.get("phone"));
                contact.setCompany((String) properties.get("company"));
                return contact;
            } else {
                throw new ExternalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid response format from HubSpot");
            }
        } catch (ExternalApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error creating contact: {}", e.getMessage());
            throw new ExternalApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public String exchangeCodeForToken(String clientId, String clientSecret, String redirectUri, String code) {
        log.info("Exchanging authorization code for token");
        
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        try {
            Map<String, Object> response = webClient.post()
                    .uri(TOKEN_URI)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            if (response != null && response.containsKey("access_token")) {
                log.info("Successfully obtained access token");
                return response.get("access_token").toString();
            } else {
                throw new ExternalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "No access_token in response");
            }
        } catch (Exception e) {
            log.error("Error exchanging code for token: {}", e.getMessage());
            throw new ExternalApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}