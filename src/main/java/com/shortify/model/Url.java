package com.shortify.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the URL", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "The original URL that was shortened", example = "https://www.example.com/loremipsumdolorsitamet")
    private String originalUrl;

    @Schema(description = "The shortened URL", example = "https://short.ly/abc123")
    private String shortenedUrl;

    @Column(nullable = false, updatable = false)
    @Schema(description = "Timestamp of when the URL was created", example = "2024-11-27T10:15:30")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public Url(String originalUrl, String shortenedUrl) {
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
    }
}