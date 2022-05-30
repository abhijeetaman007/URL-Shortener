package com.example.urlshortener.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
//@PropertySource("classpath:/application.properties")

public class UrlShort {
//    Getting Value from application.properties file similar to .env file
    @Value("${urlshort.prefix}")
    private String prefix;

    @Value("${urlshort.domain}")
    private String domain;
}
