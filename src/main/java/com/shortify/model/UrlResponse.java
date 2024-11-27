package com.shortify.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response for a shortened URL")
public class UrlResponse {
    @Schema(
            description = "The shortened path generated from the original URL",
            example = "abc123")
    private String shortenedPath;

    @Schema(
            description = "The shortened URL generated from the original URL",
            example = "https://shortify.com/abc123")
    private String shortenedUrl;

}