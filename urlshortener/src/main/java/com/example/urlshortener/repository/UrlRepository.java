package com.example.urlshortener.repository;

import com.example.urlshortener.data.entity.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository

public class UrlRepository {

//    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    //To add new url mapping to DB
    public void createUrl(Url url) {
        entityManager.merge(url);
    }

    //To get url mapping from DB
    public Url getByKey(String key) {
        return entityManager.find(Url.class, key); //returns Url object
    }

    public void deleteUrl(Url url) {
        entityManager.remove(url);
    }


}

