package com.shortify.service.impl;

import com.shortify.exception.UrlNotFoundException;
import com.shortify.model.UrlResponse;
import org.springframework.beans.factory.annotation.Value;
import com.shortify.model.Url;
import com.shortify.repository.UrlRepository;
import com.shortify.service.UrlShorteningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShorteningServiceImpl implements UrlShorteningService {

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    private final UrlRepository urlRepository;

    @Autowired
    public UrlShorteningServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlResponse shortenUrl(String originalUrl) {
        Optional<Url> existingUrl = Optional.ofNullable(urlRepository.findByOriginalUrl(originalUrl));
        if (existingUrl.isPresent()) {
            return UrlResponse.builder()
                    .shortenedPath(existingUrl.get().getShortenedPath())
                    .shortenedUrl(existingUrl.get().getShortenedUrl())
                    .build();
        }

        String shortenedPath = generateShortenedPath(originalUrl);
        String shortenedUrl = generateShortenedUrl(shortenedPath);

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortenedPath(shortenedPath)
                .shortenedUrl(shortenedUrl)
                .build();

        urlRepository.save(url);

        return UrlResponse.builder()
                .shortenedUrl(url.getShortenedUrl())
                .build();
    }

    private String generateShortenedUrl(String shortenedPath) {
        return baseUrl + shortenedPath;
    }

    private String generateShortenedPath(String originalUrl) {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    @Override
    public UrlResponse getOriginalUrl(String shortenedPath) {
        if (shortenedPath == null || shortenedPath.isEmpty()) {
            throw new IllegalArgumentException("The parameter shortenedPath cannot be null or empty.");
        }

        Url url = urlRepository.findByShortenedPath(shortenedPath);
        if (url == null) {
            throw new UrlNotFoundException(shortenedPath);
        }

        return new UrlResponse(url.getOriginalUrl(), null, null);
    }
}