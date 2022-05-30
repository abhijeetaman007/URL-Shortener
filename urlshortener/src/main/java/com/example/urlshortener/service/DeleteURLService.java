package com.example.urlshortener.service;

import com.example.urlshortener.data.entity.Url;
import com.example.urlshortener.repository.UrlRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@EnableScheduling
@Data

public class DeleteURLService {

    @Autowired
    private UrlRepository urlRepository;


    @Async //@Async annotation to execute the method asynchronously in another thread not in the main thread
//    @Scheduled(fixedRate = 5000) //@Scheduled annotation to execute the method at a fixed rate of 5 second (5000 milliseconds)
    @Scheduled(fixedRate = 120*3600 *1000)
    @Transactional
    public void deleteURL() {
//        System.out.println("Scheduling is Working");
        List<Url> urlList = urlRepository.getExpiredUrls();
        System.out.println(urlList);
        for(Url url : urlList) {
            urlRepository.deleteUrl(url);
        }
    }
}
