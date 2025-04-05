package com.igorbavand.hubspotintegration.exception;

import com.igorbavand.hubspotintegration.application.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<?> handleExternalApiException(ExternalApiException ex) {
        log.warn("External API error: status={}, message={}", ex.getStatusCode(), ex.getResponseBody());

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ApiResponse.error("Erro ao chamar API externa: " + ex.getReason()));
    }
}
