package com.example.shortify.dto;

import java.time.LocalDateTime;

public class UrlCreationRequest {
    private String originalUrl;
    private LocalDateTime expiresAt;

    public UrlCreationRequest() {
    }

    public UrlCreationRequest(String originalUrl, LocalDateTime expiresAt) {
        this.originalUrl = originalUrl;
        this.expiresAt = expiresAt;
    }


    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}