package com.example.urlshortener.service;

import com.example.urlshortener.config.UrlShort;
import com.example.urlshortener.data.entity.Url;
import com.example.urlshortener.data.request.CreateUrlRequest;
import com.example.urlshortener.data.response.CreateUrlResponse;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.utility.HashUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private HashUtility hashUtility;

    @Autowired
    private UrlShort urlShort;

    public String createUrl(CreateUrlRequest createUrlRequest) {
        Url url = Url.builder().originalUrl(createUrlRequest.getOriginalUrl()).userId(createUrlRequest.getUserId()).createdAt(new Date()).urlkey(hashUtility.getKey(createUrlRequest.getOriginalUrl())).build();

        urlRepository.createUrl(url);
        String shortUrl = urlShort.getDomain() + urlShort.getPrefix() + url.getUrlkey();
        return shortUrl;
    }

    public String getOriginalUrl(String urlkey) {
        if(urlkey != null) {
            Url url = urlRepository.getByKey(urlkey);
            return url.getOriginalUrl();
        }
        return null;
    }

    public void deleteUrl(String urlkey) {
        if(urlkey != null) {
            Url url = urlRepository.getByKey(urlkey);
            urlRepository.deleteUrl(url);
        }
    }
}
