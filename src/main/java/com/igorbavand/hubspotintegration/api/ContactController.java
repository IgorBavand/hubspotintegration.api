package com.igorbavand.hubspotintegration.api;

import com.igorbavand.hubspotintegration.application.dto.request.ContactRequest;
import com.igorbavand.hubspotintegration.application.service.ContactService;
import com.igorbavand.hubspotintegration.exception.ExternalApiException;
import com.igorbavand.hubspotintegration.infrastructure.utils.TokenParser;
import com.igorbavand.hubspotintegration.application.dto.response.ApiResponse;
import com.igorbavand.hubspotintegration.application.dto.response.ContactResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final TokenParser tokenParser;

    @PostMapping
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(
            @RequestHeader("Authorization") String accessToken,
            @Valid @RequestBody ContactRequest request) {

        if (accessToken == null || accessToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Access token is required"));
        }

        try {
            String cleanToken = tokenParser.parse(accessToken);
            ContactResponse contact = contactService.createContact(cleanToken, request);
            return ResponseEntity.ok(ApiResponse.success(contact));
        } catch (ExternalApiException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(ApiResponse.error("HubSpot error: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Unexpected error: " + e.getMessage()));
        }
    }
}
