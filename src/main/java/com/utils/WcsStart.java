package com.utils;

import com.utils.config.ConfigsPoJo;
import com.utils.spring.SpringBootUtil;
import com.utils.threadpool.AsyncService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WcsStart {
    public static void main(String[] args) {

        SpringApplication.run(WcsStart.class, args);
        //ConfigsPoJo configsPoJo = (ConfigsPoJo) SpringBootUtil.getBean(ConfigsPoJo.class);
        //Integer pathLockLength = configsPoJo.getPathLockLength();
        //System.out.println(pathLockLength);
        //RedisTemplate redisTemplate = (RedisTemplate) SpringBootUtil.getcBean("redisTemplate");
        //Set keys = redisTemplate.keys("*");
        //System.out.println(keys);

        AsyncService asyncService = (AsyncService) SpringBootUtil.getBean(AsyncService.class);
        for (int i = 0; i <100 ; i++) {
            System.err.println("start submit--------------"+i);
            //调用service层的任务
            asyncService.executeAsync();
            System.err.println("end submit---------------"+i);
        }
    }
}
