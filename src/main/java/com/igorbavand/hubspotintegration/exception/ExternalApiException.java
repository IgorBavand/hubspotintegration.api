package com.igorbavand.hubspotintegration.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ExternalApiException extends ResponseStatusException {

    private final String responseBody;

    public ExternalApiException(HttpStatusCode status, String responseBody) {
        super(status, extractMessage(responseBody));
        this.responseBody = responseBody;
    }

    public boolean isRetryable() {
        return getStatusCode().is5xxServerError();
    }

    private static String extractMessage(String body) {
        return body;
    }
}
