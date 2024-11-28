package com.shortify.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Response for a shortened URL")
public class UrlResponse {

    @Schema(
            description = "The original URL that was shortened",
            example = "www.example.com/looooooong-url")
    private String originalUrl;

    @Schema(
            description = "The shortened path generated from the original URL",
            example = "12e6e")
    private String shortenedPath;

    @Schema(
            description = "The shortened URL generated from the original URL",
            example = "https://shortify.ly/12e6e")
    private String shortenedUrl;
}