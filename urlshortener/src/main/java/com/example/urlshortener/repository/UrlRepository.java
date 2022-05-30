package com.example.urlshortener.repository;

import com.example.urlshortener.data.entity.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

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

//    Query to fetch all the urls which needs to be deleted
    public List<Url> getExpiredUrls(){
        //We will write a criteria query here
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Url> cq = cb.createQuery(Url.class);
        Root<Url> root = cq.from(Url.class);
        cq.select(root).where(cb.lessThanOrEqualTo(root.get("expirationDate"), new Date()));

        TypedQuery<Url> query = entityManager.createQuery(cq);
        return query.getResultList();

    }


}

