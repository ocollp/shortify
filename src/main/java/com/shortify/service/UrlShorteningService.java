package com.shortify.service;

public interface UrlShorteningService {
    String generateFullShortenedUrl(String shortenedPath);
    String shortenUrl(String originalUrl);
    String getOriginalUrl(String shortenedPath);
}