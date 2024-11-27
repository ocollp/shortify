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
        testUrl.setOriginalUrl("http://example.com/blablabla");
        testUrl.setShortenedPath("174c74d6");
        testUrl.setShortenedUrl("http://shortify.com/174c74d6");
        testUrl.setId(UUID.randomUUID());
        urlRepository.save(testUrl);
    }

    @Test
    public void testFindByShortenedUrl() {
        Url foundUrl = urlRepository.findByShortenedUrl("http://shortify.com/174c74d6");
        assertNotNull(foundUrl);
        assertEquals(testUrl.getOriginalUrl(), foundUrl.getOriginalUrl());
    }

    @Test
    public void testFindByOriginalUrl() {
        Url foundUrl = urlRepository.findByOriginalUrl("http://example.com/blablabla");
        assertNotNull(foundUrl);
        assertEquals(testUrl.getShortenedUrl(), foundUrl.getShortenedUrl());
    }
}