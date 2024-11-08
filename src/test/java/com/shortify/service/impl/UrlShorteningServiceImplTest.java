package com.shortify.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.shortify.model.Url;
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
        ReflectionTestUtils.setField(urlShorteningService, "baseUrl", "http://shortify.com/"); // Inject mock base URL
    }

    @Test
    public void testShortenNewUrl() {
        String originalUrl = "http://shortify.com/";
        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(null);
        when(urlRepository.findByShortenedUrl(anyString())).thenReturn(null);

        String result = urlShorteningService.shortenUrl(originalUrl);

        assertTrue(result.contains("URL successfully shortened:"));
        verify(urlRepository).save(any(Url.class));
    }

    @Test
    public void testShortenUrlWithConflict() {
        String originalUrl = "http://shortify.com/";
        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(null);
        when(urlRepository.findByShortenedUrl(anyString())).thenReturn(new Url());

        String result = urlShorteningService.shortenUrl(originalUrl);

        assertEquals("A conflict occurred: the shortened path already exists. Please try again.", result);
    }

    @Test
    public void testGetOriginalUrlValidShortenedPath() {
        String shortenedPath = "abcd1234";
        String originalUrl = "http://shortify.com/";
        when(urlRepository.findByShortenedUrl(shortenedPath)).thenReturn(new Url(originalUrl, shortenedPath));

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
        assertThrows(IllegalArgumentException.class, () -> urlShorteningService.getOriginalUrl(""));
    }

    @Test
    public void testGetOriginalUrlWithNullPath() {
        assertThrows(IllegalArgumentException.class, () -> urlShorteningService.getOriginalUrl(null));
    }
}