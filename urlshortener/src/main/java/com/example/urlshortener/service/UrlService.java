package com.example.urlshortener.service;

import com.example.urlshortener.config.ExpirationConfig;
import com.example.urlshortener.config.UrlShort;
import com.example.urlshortener.data.entity.Url;
import com.example.urlshortener.data.request.CreateUrlRequest;
import com.example.urlshortener.data.response.CreateUrlResponse;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.utility.HashUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private HashUtility hashUtility;

    @Autowired
    private UrlShort urlShort;

    @Autowired
    private ExpirationConfig expirationConfig;

    public String createUrl(CreateUrlRequest createUrlRequest) {
        Url url;
        if(createUrlRequest.getAlias() == null) {
            url = Url.builder().originalUrl(createUrlRequest.getOriginalUrl()).userId(createUrlRequest.getUserId()).createdAt(new Date()).urlkey(hashUtility.getKey(createUrlRequest.getOriginalUrl())).expirationDate(getExpiryDate()).build();
        }else{
            if(urlRepository.getByKey(createUrlRequest.getAlias()) == null) {
                url = Url.builder().originalUrl(createUrlRequest.getOriginalUrl()).userId(createUrlRequest.getUserId()).createdAt(new Date()).urlkey(createUrlRequest.getAlias()).expirationDate(getExpiryDate()).build();
            }
            else {
                return null;
            }
        }
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

//    Method to get the expiry date based on the config -- used while creating the url entry in the database
    private Date getExpiryDate(){
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, expirationConfig.getExpirationTime());
        return calendar.getTime();
    }

}
