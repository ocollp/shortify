package com.shortify.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the URL", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID id;

    @Column(nullable = false)
    @Schema(description = "The original URL that was shortened", example = "www.example.com/looooooong-url")
    private String originalUrl;

    @Schema(description = "The shortened path", example = "12e6e")
    private String shortenedPath;

    @Schema(description = "The shortened URL", example = "shortify.ly/12e6e")
    private String shortenedUrl;

    public Url(String originalUrl, String shortenedPath, String shortenedUrl) {
        this.originalUrl = originalUrl;
        this.shortenedPath = shortenedPath;
        this.shortenedUrl = shortenedUrl;
    }
}