package com.igorbavand.hubspotintegration.api;

import com.igorbavand.hubspotintegration.application.dto.request.HubspotWebhookRequest;
import com.igorbavand.hubspotintegration.application.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhook")
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping
    public void webhookHubSpot(@RequestBody List<HubspotWebhookRequest> payloads) {
        payloads.forEach(webhookService::sendNotificationWebhook);
    }
}
