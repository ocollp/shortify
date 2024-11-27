package com.shortify.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "URL shortening request")
public class UrlRequest {
    @Schema(
            description = "The original URL that needs to be shortened",
            example = "https://www.example.com/loremipsumdolorsitamet")
    private String url;
}