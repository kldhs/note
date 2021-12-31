package com.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class TimeTaskForPush {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    private static final int TIME = 1000 * 5 * 1;

    @Scheduled(fixedRate = TIME)
    public void pushTimeTask() {
        Set<String> keys = stringRedisTemplate.keys("*");
        System.out.println("11111111111111");

    }



}
