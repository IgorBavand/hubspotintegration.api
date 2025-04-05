package com.igorbavand.hubspotintegration.application.service;

import com.igorbavand.hubspotintegration.application.dto.request.HubspotWebhookRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebhookService {

    public void sendNotificationWebhook(HubspotWebhookRequest payload) {
        if (payload.getSubscriptionType().equals("contact.creation")) {
            log.info("Contact confirmed for hubspot webhook");
        }
    }
}
