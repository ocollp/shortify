package com.shortify.exception;

import com.shortify.model.ErrorResponse;

public class UrlNotFoundException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public UrlNotFoundException(String shortenedPath) {
        super("No URL found for shortened path: " + shortenedPath);
        this.errorResponse = new ErrorResponse(
                "404",
                "URL Not Found: " + shortenedPath
        );
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}