package com.example.shortify.controller;

import com.example.shortify.dto.UrlCreationRequest;
import com.example.shortify.dto.UrlResponse;
import com.example.shortify.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/urls")
    public ResponseEntity<UrlResponse> createShortUrl(@RequestBody UrlCreationRequest request) {
        UrlResponse response = urlService.createShortUrl(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/api/urls")
    public ResponseEntity<List<UrlResponse>> getAllUrls() {
        List<UrlResponse> urls = urlService.getAllUrls();
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/api/urls/{shortCode}")
    public ResponseEntity<UrlResponse> getUrlInfo(@PathVariable String shortCode) {
        return urlService.getUrlInfo(shortCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/s/{shortCode}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode) {
        Optional<String> originalUrlOpt = urlService.getOriginalUrl(shortCode);

        if (originalUrlOpt.isPresent()) {
            String originalUrl = originalUrlOpt.get();

            if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
                originalUrl = "http://" + originalUrl;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", originalUrl);
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("URL non trovato o scaduto");
        }
    }

    @GetMapping("/not-found")
    public ResponseEntity<String> notFound() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("URL non trovato o scaduto");
    }
}