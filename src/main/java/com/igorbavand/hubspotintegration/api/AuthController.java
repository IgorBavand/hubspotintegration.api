package com.igorbavand.hubspotintegration.api;

import com.igorbavand.hubspotintegration.application.service.AuthService;
import com.igorbavand.hubspotintegration.application.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/authorize")
    public ResponseEntity<ApiResponse<String>> getAuthorizationUrl() {
        try {
            String authUrl = authService.generateAuthorizationUrl();
            return ResponseEntity.ok(ApiResponse.success(authUrl));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error generating authorization URL: " + e.getMessage()));
        }
    }

    @GetMapping("/callback")
    public ResponseEntity<ApiResponse<String>> handleCallback(@RequestParam("code") String code) {
        if (code == null || code.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Authorization code is required"));
        }

        try {
            String token = authService.exchangeCodeForToken(code);
            return ResponseEntity.ok(ApiResponse.success(token));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Error exchanging code for token: " + e.getMessage()));
        }
    }
}
