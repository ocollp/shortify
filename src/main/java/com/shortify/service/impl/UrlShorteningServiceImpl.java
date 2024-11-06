package com.shortify.service.impl;

import org.springframework.beans.factory.annotation.Value;
import com.shortify.model.Url;
import com.shortify.repository.UrlRepository;
import com.shortify.service.UrlShorteningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Optional;

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
    public String generateFullShortenedUrl(String shortenedPath) {
        return baseUrl + shortenedPath;
    }

    @Override
    public String shortenUrl(String originalUrl) {
        Optional<Url> existingUrl = Optional.ofNullable(urlRepository.findByOriginalUrl(originalUrl));

        if (existingUrl.isPresent()) {
            return "The URL already exists. Shortened URL: " + existingUrl.get().getShortenedUrl();
        }

        String shortenedPath = generateShortenedPath(originalUrl);

        Optional<Url> pathExists = Optional.ofNullable(urlRepository.findByShortenedUrl(shortenedPath));
        if (pathExists.isPresent()) {
            return "A conflict occurred: the shortened path already exists. Please try again.";
        }

        String shortenedUrl = generateShortenedUrl(shortenedPath);

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortenedUrl(shortenedPath);

        urlRepository.save(url);

        return "URL successfully shortened: " + shortenedUrl;
    }

    private String generateShortenedUrl(String originalUrl) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String uniquePath = generateShortenedPath(originalUrl);
        return baseUrl + "/shortify/" + uniquePath;
    }

    private String generateShortenedPath(String originalUrl) {
        String uniqueId = Base64.getUrlEncoder().encodeToString(originalUrl.getBytes()).substring(0, 8);
        return uniqueId.replace("=", "");
    }

    @Override
    public String getOriginalUrl(String shortenedPath) {
        if (shortenedPath == null || shortenedPath.isEmpty()) {
            throw new IllegalArgumentException("The parameter shortenedPath cannot be null or empty.");
        }

        Url url = urlRepository.findByShortenedUrl(shortenedPath);
        return url != null ? url.getOriginalUrl() : null;
    }
}