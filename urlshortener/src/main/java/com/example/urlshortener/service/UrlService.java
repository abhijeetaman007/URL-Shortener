package com.example.urlshortener.service;

import com.example.urlshortener.config.ExpirationConfig;
import com.example.urlshortener.config.UrlShort;
import com.example.urlshortener.data.entity.Url;
import com.example.urlshortener.data.request.CreateUrlRequest;
import com.example.urlshortener.data.response.CreateUrlResponse;
import com.example.urlshortener.repository.UrlRepository;
import com.example.urlshortener.utility.CacheUtility;
import com.example.urlshortener.utility.HashUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private CacheUtility<String> cacheUtility;

    private ObjectMapper mapper;

    public UrlService(){
        mapper = new ObjectMapper(); //Object Mapper is used to convert Java Object to JSON and String

    }


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

        try {
            //  Check if the url is present in the cache
            if (cacheUtility.getValue(urlkey) != null) {
                String cachedValue = cacheUtility.getValue(urlkey);
                if (cachedValue != null) {
//                    Deserialize the JSON String to Java Object
                    System.out.println("Got Cached Value from RedisCache" + cachedValue);
                    Url cachedUrl = mapper.readValue(cachedValue, Url.class);
                    return cachedUrl.getOriginalUrl();
                }
            }

            if (urlkey != null) {
                Url url = urlRepository.getByKey(urlkey);

                // If not present in the cache, then cache it
                //cacheUtility.setValue(urlkey, url); //This cant be used we need to use Object Mapper to convert it to string and the convert back to object
                System.out.println("Caching Value to RedisCache "+mapper.writeValueAsString(url));
                cacheUtility.setValue(urlkey, mapper.writeValueAsString(url));
                if (url != null) {
                    return url.getOriginalUrl();
                }
            }
        }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
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
