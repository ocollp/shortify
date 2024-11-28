package com.shortify.controller;

import com.shortify.model.UrlRequest;
import com.shortify.model.UrlResponse;
import com.shortify.service.UrlShorteningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class UrlShorteningControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UrlShorteningService urlShorteningService;

    @InjectMocks
    private UrlShorteningController urlShorteningController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(urlShorteningController).build();
    }

    @Test
    public void testShortenUrl() throws Exception {
        String originalUrl = "www.example.com/looooooong-url";
        String shortenedPath = "174c7";
        String shortenedUrl = "shortify.ly/174c7";

        UrlResponse urlResponse = new UrlResponse(null, shortenedPath, shortenedUrl);
        when(urlShorteningService.shortenUrl(originalUrl)).thenReturn(urlResponse);

        mockMvc.perform(post("/api/shortify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\": \"" + originalUrl + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenedUrl").value(shortenedUrl));
    }
}