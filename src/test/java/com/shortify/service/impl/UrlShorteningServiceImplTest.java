package com.shortify.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.shortify.exception.UrlNotFoundException;
import com.shortify.model.Url;
import com.shortify.model.UrlResponse;
import com.shortify.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

@ExtendWith(MockitoExtension.class)
public class UrlShorteningServiceImplTest {

    @Value("${base.url}")
    private String baseUrl;

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShorteningServiceImpl urlShorteningService;

    @Test
    public void testGetOriginalUrlValidShortenedPath() {
        String shortenedPath = "174c7";
        String originalUrl = "www.example.com/looooooong-url";
        String shortenedUrl = "shortify.ly/174c7";
        Url url = new Url(originalUrl, shortenedPath, shortenedUrl);

        when(urlRepository.findByShortenedPath(shortenedPath)).thenReturn(url);

        UrlResponse urlResponse = urlShorteningService.getOriginalUrl(shortenedPath);

        assertEquals(originalUrl, urlResponse.getOriginalUrl());
    }

    @Test
    public void testGetOriginalUrlInvalidShortenedPath() {
        String shortenedPath = "nonexistent";

        when(urlRepository.findByShortenedPath(shortenedPath)).thenReturn(null);

        UrlNotFoundException exception = assertThrows(UrlNotFoundException.class,
                () -> urlShorteningService.getOriginalUrl(shortenedPath));

        assertEquals("No URL found for shortened path: nonexistent", exception.getMessage());
    }

    @Test
    public void testGetOriginalUrlWithEmptyPath() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> urlShorteningService.getOriginalUrl(""));
        assertEquals("The parameter shortenedPath cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void testGetOriginalUrlWithNullPath() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> urlShorteningService.getOriginalUrl(null));
        assertEquals("The parameter shortenedPath cannot be null or empty.", exception.getMessage());
    }
}