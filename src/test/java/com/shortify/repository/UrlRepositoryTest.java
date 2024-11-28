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
        testUrl.setOriginalUrl("www.example.com/looooooong-url");
        testUrl.setShortenedPath("174c7");
        testUrl.setShortenedUrl("shortify.ly/174c7");
        testUrl.setId(UUID.randomUUID());
        urlRepository.save(testUrl);
    }

    @Test
    public void testFindByShortenedUrl() {
        Url foundUrl = urlRepository.findByShortenedUrl("shortify.ly/174c7");
        assertNotNull(foundUrl);
        assertEquals(testUrl.getOriginalUrl(), foundUrl.getOriginalUrl());
    }

    @Test
    public void testFindByOriginalUrl() {
        Url urlToInsert = new Url();
        urlToInsert.setOriginalUrl("www.example.com/very-long-url");
        urlToInsert.setShortenedPath("abcd123");
        urlToInsert.setShortenedUrl("http://short.ly/abcd123");
        urlRepository.save(urlToInsert);

        Url foundUrl = urlRepository.findByOriginalUrl("www.example.com/very-long-url");

        assertNotNull(foundUrl);

        assertEquals(urlToInsert.getShortenedUrl(), foundUrl.getShortenedUrl());
    }
}