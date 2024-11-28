package com.shortify.controller;

import com.shortify.service.UrlShorteningService;
import com.shortify.model.UrlResponse;
import com.shortify.model.UrlRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shortify")
public class UrlShorteningController {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @PostMapping
    @Operation(summary = "Shorten a URL", description = "Shortens a given URL and returns the shortened version.")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest urlRequest) {
        UrlResponse urlResponse = urlShorteningService.shortenUrl(urlRequest.getUrl());
        return ResponseEntity.ok(urlResponse);
    }

    @GetMapping("/{shortenedPath}")
    @Operation(summary = "Redirect to Original URL", description = "Redirects the shortened URL to the original one.")
    public ResponseEntity<UrlResponse> redirectUrl(@PathVariable String shortenedPath) {
        UrlResponse urlResponse = urlShorteningService.getOriginalUrl(shortenedPath);
        return ResponseEntity.ok(urlResponse);
    }
}