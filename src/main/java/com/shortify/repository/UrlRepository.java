package com.shortify.repository;

import com.shortify.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url, UUID> {
    Url findByShortenedUrl(String shortenedUrl);
    Url findByOriginalUrl(String originalUrl);
}