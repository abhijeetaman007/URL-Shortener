package com.example.urlshortener.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@Configuration
public class CacheUtility <T>{

        public RedisTemplate<String,T> redisTemplate;
        private ValueOperations<String,T> valueOperations; //ValueOperations is a class which is used to perform operations on the values stored in Redis

        @Autowired
        CacheUtility(RedisTemplate<String,T> redisTemplate){
            this.redisTemplate = redisTemplate;
            this.valueOperations = redisTemplate.opsForValue();
        }

        public void setValue(String key,T value){
            valueOperations.set(key,value);
        }

        public T getValue(String key){
            return valueOperations.get(key);
        }


}
