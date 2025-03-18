package com.example.shortify.dto;

import java.time.LocalDateTime;

public class UrlResponse {
    private String originalUrl;
    private String shortUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Integer clickCount;

    public UrlResponse() {
    }

    public UrlResponse(String originalUrl, String shortUrl, String shortCode,
                       LocalDateTime createdAt, LocalDateTime expiresAt, Integer clickCount) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.clickCount = clickCount;
    }


    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
}