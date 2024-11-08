package com.shortify.service;

public interface UrlShorteningService {
    String shortenUrl(String originalUrl);
    String getOriginalUrl(String shortenedPath);
}