package com.igorbavand.hubspotintegration.infrastructure.utils;

import org.springframework.stereotype.Component;

@Component
public class TokenParser {

    public String parse(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}