package com.igorbavand.hubspotintegration.infrastructure.utils;

import com.igorbavand.hubspotintegration.application.dto.request.ContactRequest;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ConvertHubspotContactPayload {

    public static Map<String, Object> convert(ContactRequest request) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("email", request.getEmail());
        properties.put("firstname", request.getFirstname());
        properties.put("lastname", request.getLastname());
        properties.put("phone", request.getPhone());
        properties.put("company", request.getCompany());

        Map<String, Object> payload = new HashMap<>();
        payload.put("properties", properties);
        return payload;
    }
}
