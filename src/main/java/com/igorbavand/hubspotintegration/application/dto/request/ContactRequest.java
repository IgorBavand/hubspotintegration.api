package com.igorbavand.hubspotintegration.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequest {

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phone;

    private String company;

    private String website;
}
