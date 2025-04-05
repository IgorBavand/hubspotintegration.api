package com.igorbavand.hubspotintegration.application.dto.response;

import lombok.Data;

@Data
public class ContactResponse {
    private String email;
    private String firstname;
    private String lastname;
    private String phone;
    private String company;
} 