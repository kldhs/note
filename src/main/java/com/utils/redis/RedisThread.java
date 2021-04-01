package com.utils.redis;

import com.utils.WcsStart;
import com.utils.spring.SpringBootUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

/**
 * @author xs
 * @date 2020/11/21 09:06
 */
public class RedisThread {
    public static void main(String[] args) {
        SpringApplication.run(WcsStart.class, args);


        RedisTemplate redisTemplate = (RedisTemplate) SpringBootUtil.getBean("redisTemplate");
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        Set keys = redisTemplate.keys("*");
        System.out.println(keys);
        //redisTemplate.opsForValue().set("WcsMessage", "11");

    }
}
