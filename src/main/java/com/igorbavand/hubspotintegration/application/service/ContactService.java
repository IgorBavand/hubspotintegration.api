package com.igorbavand.hubspotintegration.application.service;

import com.igorbavand.hubspotintegration.application.dto.request.ContactRequest;
import com.igorbavand.hubspotintegration.infrastructure.client.HubspotWebClient;
import com.igorbavand.hubspotintegration.application.dto.response.ContactResponse;
import com.igorbavand.hubspotintegration.infrastructure.utils.ConvertHubspotContactPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {

    private final HubspotWebClient hubspotClient;

    public ContactResponse createContact(String accessToken, ContactRequest request) {
        return hubspotClient.createContact(accessToken, ConvertHubspotContactPayload.convert(request));
    }
}
