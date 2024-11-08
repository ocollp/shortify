package com.shortify.repository;

import com.shortify.model.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    private Url testUrl;

    @BeforeEach
    public void setUp() {
        testUrl = new Url();
        testUrl.setOriginalUrl("https://shortify.com");
        testUrl.setShortenedUrl("short123");
        testUrl.setId(UUID.randomUUID());

        urlRepository.save(testUrl);
    }

    @Test
    public void testFindByShortenedUrl() {
        Url foundUrl = urlRepository.findByShortenedUrl("short123");
        assertNotNull(foundUrl);
        assertEquals(testUrl.getOriginalUrl(), foundUrl.getOriginalUrl());
    }

    @Test
    public void testFindByOriginalUrl() {
        Url foundUrl = urlRepository.findByOriginalUrl("https://shortify.com");
        assertNotNull(foundUrl);
        assertEquals(testUrl.getShortenedUrl(), foundUrl.getShortenedUrl());
    }
}