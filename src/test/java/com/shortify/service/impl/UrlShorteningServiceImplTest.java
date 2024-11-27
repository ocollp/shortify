package com.shortify.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.shortify.model.Url;
import com.shortify.model.UrlResponse;
import com.shortify.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class UrlShorteningServiceImplTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShorteningServiceImpl urlShorteningService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(urlShorteningService, "baseUrl", "http://shortify.com");
    }

    @Test
    public void testShortenNewUrl() {
        String originalUrl = "http://example.com/blablablablablabla";

        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(null);

        Url capturedUrl = new Url();
        doAnswer(invocation -> {
            Url url = invocation.getArgument(0);
            capturedUrl.setOriginalUrl(url.getOriginalUrl());
            capturedUrl.setShortenedPath(url.getShortenedPath());
            capturedUrl.setShortenedUrl(url.getShortenedUrl());
            return null;
        }).when(urlRepository).save(any(Url.class));

        UrlResponse urlResponse = urlShorteningService.shortenUrl(originalUrl);

        assertNotNull(urlResponse.getShortenedPath());
        assertNotNull(urlResponse.getShortenedUrl());

        verify(urlRepository).save(any(Url.class));
        assertEquals(originalUrl, capturedUrl.getOriginalUrl());
    }

    @Test
    public void testShortenExistingUrl() {
        String originalUrl = "http://example.com/blablablablablabla";
        String shortenedPath = "174c74d6";
        String shortenedUrl = "http://shortify.com/" + shortenedPath;

        Url existingUrl = new Url(originalUrl, shortenedPath, shortenedUrl);

        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(existingUrl);

        UrlResponse urlResponse = urlShorteningService.shortenUrl(originalUrl);

        assertEquals(shortenedPath, urlResponse.getShortenedPath());
        assertEquals(shortenedUrl, urlResponse.getShortenedUrl());
        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    public void testGetOriginalUrlValidShortenedPath() {
        String shortenedPath = "174c74d6";
        String shortenedUrl = "http://shortify.com/174c74d6";
        String originalUrl = "http://example.com/blablablablablabla";

        Url url = new Url(originalUrl, shortenedPath, shortenedUrl);

        when(urlRepository.findByShortenedUrl(shortenedPath)).thenReturn(url);

        String result = urlShorteningService.getOriginalUrl(shortenedPath);

        assertEquals(originalUrl, result);
    }

    @Test
    public void testGetOriginalUrlInvalidShortenedPath() {
        String shortenedPath = "nonexistent";

        when(urlRepository.findByShortenedUrl(shortenedPath)).thenReturn(null);

        String result = urlShorteningService.getOriginalUrl(shortenedPath);

        assertNull(result);
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