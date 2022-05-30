package com.example.urlshortener.apis;

import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/ush")

public class RedirectionController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/{key}")
    @Transactional
    public ResponseEntity redirect(@PathVariable String key) {
        ResponseEntity res = null;
        try {
            String url = urlService.getOriginalUrl(key);
            res = ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(new URI(url)).build();

        }
        //URI uri = new URI("https://www.google.com"); can throw URISyntaxException
        catch (URISyntaxException e) {

            res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return res;
    }
}