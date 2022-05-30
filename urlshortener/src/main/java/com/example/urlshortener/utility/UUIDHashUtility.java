package com.example.urlshortener.utility;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDHashUtility implements HashUtility {
    public String getKey(String originalUrl) {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8);
    }
}
