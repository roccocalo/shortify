package com.example.shortify.service;

import com.example.shortify.Repository.UrlRepository;
import com.example.shortify.dto.UrlCreationRequest;
import com.example.shortify.dto.UrlResponse;
import com.example.shortify.model.Url;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private static final Random RANDOM = new Random();

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public UrlResponse createShortUrl(UrlCreationRequest request) {
        if (request.getOriginalUrl() == null || request.getOriginalUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("L'URL originale non può essere vuoto");
        }

        LocalDateTime expiresAt = request.getExpiresAt();
        if (expiresAt == null) {
            expiresAt = LocalDateTime.now().plusDays(7);
        }

        String shortCode = generateUniqueShortCode();

        Url url = new Url(request.getOriginalUrl(), shortCode, expiresAt);
        url = urlRepository.save(url);

        return buildUrlResponse(url);
    }

    @Transactional(readOnly = true)
    public List<UrlResponse> getAllUrls() {
        return urlRepository.findAll().stream()
                .map(this::buildUrlResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<String> getOriginalUrl(String shortCode) {
        Optional<Url> urlOptional = urlRepository.findByShortCode(shortCode);

        if (urlOptional.isPresent()) {
            Url url = urlOptional.get();

            if (url.getExpiresAt() != null && url.getExpiresAt().isBefore(LocalDateTime.now())) {
                System.out.println("URL scaduto");
                return Optional.empty();
            }

            url.incrementClickCount();
            urlRepository.save(url);

            System.out.println("URL trovato nel DB per shortCode: " + url.getOriginalUrl());
            return Optional.of(url.getOriginalUrl());
        }
        else {
            return Optional.empty();
        }


    }

    @Transactional(readOnly = true)
    public Optional<UrlResponse> getUrlInfo(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(this::buildUrlResponse);
    }

    private String generateUniqueShortCode() {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (urlRepository.existsByShortCode(shortCode));

        return shortCode;
    }

    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    private UrlResponse buildUrlResponse(Url url) {
        String shortUrl = baseUrl + "/s/" + url.getShortCode();

        return new UrlResponse(
                url.getOriginalUrl(),
                shortUrl,
                url.getShortCode(),
                url.getCreatedAt(),
                url.getExpiresAt(),
                url.getClickCount()
        );
    }
}