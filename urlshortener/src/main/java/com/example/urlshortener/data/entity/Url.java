package com.example.urlshortener.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "url")
@Table(name = "url")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Url {

    @Id //We will pass the url key or we will generate it so need of generator
    private String urlkey;
    private Date createdAt;
    private Date expirationDate;
    private String userId;
    private String originalUrl;


}
