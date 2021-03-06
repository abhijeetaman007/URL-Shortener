package com.example.urlshortener.apis;

import com.example.urlshortener.data.request.CreateUrlRequest;
import com.example.urlshortener.data.response.CreateUrlResponse;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/url")

public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/create")
    @Transactional //Making whole API transactional
    public CreateUrlResponse create(@RequestBody CreateUrlRequest createUrlRequest) {
//        return CreateUrlResponse.builder().build();
        System.out.println("CreateUrlRequest: " + createUrlRequest);
        String url = urlService.createUrl(createUrlRequest);
        if(url == null) {
            return CreateUrlResponse.builder().isSuccess(false).msg("Alias already exists").build();
        }
        return CreateUrlResponse.builder()
                .isSuccess(true)
                .url(url)
                .msg("Url generated Successfully")
                .build();

    }

    @DeleteMapping("/delete/{key}")
    @Transactional
    public ResponseEntity delete(@PathVariable String key) {
//        return ResponseEntity.ok().build();
        System.out.println("Key: " + key);
           urlService.deleteUrl(key);
           return ResponseEntity.ok().build();
    }
}
