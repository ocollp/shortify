package com.shortify.service.impl;

import com.shortify.model.UrlResponse;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import com.shortify.model.Url;
import com.shortify.repository.UrlRepository;
import com.shortify.service.UrlShorteningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

    @Value("${base.url}")
    private String baseUrl;

    private final UrlRepository urlRepository;

    @Autowired
    public UrlShorteningServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlResponse shortenUrl(String originalUrl) {
        Optional<Url> existingUrl = Optional.ofNullable(urlRepository.findByOriginalUrl(originalUrl));
        if (existingUrl.isPresent()) {
            return new UrlResponse(existingUrl.get().getShortenedPath(), existingUrl.get().getShortenedUrl());
        }

        String shortenedPath = generateShortenedPath(originalUrl);
        String shortenedUrl = generateShortenedUrl(shortenedPath);

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortenedPath(shortenedPath);
        url.setShortenedUrl(shortenedUrl);
        urlRepository.save(url);

        return new UrlResponse(url.getShortenedPath(), url.getShortenedUrl());
    }

    private String generateShortenedUrl(String shortenedPath) {
        return baseUrl + shortenedPath;
    }

    private String generateShortenedPath(String originalUrl) {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public String getOriginalUrl(String shortenedPath) {
        if (shortenedPath == null || shortenedPath.isEmpty()) {
            throw new IllegalArgumentException("The parameter shortenedPath cannot be null or empty.");
        }

        Url url = urlRepository.findByShortenedPath(shortenedPath);
        return url != null ? url.getOriginalUrl() : null;
    }
}