package com.shortify.controller;

import com.shortify.service.UrlShorteningService;
import com.shortify.model.UrlResponse;
import com.shortify.model.UrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class UrlShorteningController {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @PostMapping("/shortify")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest urlRequest) {
        String shortenedUrl = urlShorteningService.shortenUrl(urlRequest.getUrl());
        return ResponseEntity.ok(new UrlResponse(shortenedUrl));
    }

    @GetMapping("/shortify/{shortenedPath}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortenedPath) {
        String originalUrl = urlShorteningService.getOriginalUrl(shortenedPath);
        return ResponseEntity.status(301).location(URI.create(originalUrl)).build();
    }
}