package com.example.urlshortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration // @Configuration indicates that this class is a configuration class
public class RedisConfiguration {

    @Bean //@Bean is a Spring annotation that tells Spring to create a bean for you as done in the following method.
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
        return new JedisConnectionFactory(config);
    }

    @Bean //When ever this bean is used it will have connection as used in above method.
    public RedisTemplate<String,Object> redisTemplate(){
        final RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // set key serializer
        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<Object>(Object.class)); // set hash key serializer
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer()); // set value serializer
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer()); // set hash value serializer

//        RedisTemplate is having jedisConnectionFactory called which connects to Redis server --> as defined in the above method jedisConnectionFactory()
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

}
