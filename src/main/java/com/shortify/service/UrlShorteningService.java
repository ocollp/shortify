package com.shortify.service;

import com.shortify.model.UrlResponse;

public interface UrlShorteningService {
    UrlResponse shortenUrl(String originalUrl);
    UrlResponse getOriginalUrl(String origin);
}