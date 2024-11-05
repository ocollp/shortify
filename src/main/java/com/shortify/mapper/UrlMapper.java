package com.shortify.mapper;

import com.shortify.model.Url;
import com.shortify.model.UrlRequest;
import com.shortify.model.UrlResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UrlMapper {

    UrlMapper INSTANCE = Mappers.getMapper(UrlMapper.class);

    Url urlRequestToUrl(UrlRequest urlRequest);

    UrlResponse urlToUrlResponse(Url url);
}