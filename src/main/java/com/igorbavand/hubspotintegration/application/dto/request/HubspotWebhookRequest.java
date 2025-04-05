package com.igorbavand.hubspotintegration.application.dto.request;

import lombok.Data;

@Data
public class HubspotWebhookRequest {
    private Long appId;
    private Long eventId;
    private Long subscriptionId;
    private Long portalId;
    private Long occurredAt;
    private String subscriptionType;
    private Integer attemptNumber;
    private Long objectId;
    private String changeSource;
    private String changeFlag;

}
