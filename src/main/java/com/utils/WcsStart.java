package com.utils;

import com.utils.spring.SpringBootUtil;
import com.utils.threadpool.executorsandspring.AsyncService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WcsStart {

    public static void main(String[] args) {
        SpringApplication.run(WcsStart.class, args);
        for (int i = 0; i <=10 ; i++) {
            AsyncService asyncService = (AsyncService) SpringBootUtil.getBean(AsyncService.class);
            System.err.println(i+"----start submit");
            //调用service层的任务
            asyncService.executeAsync();
            System.err.println(i+"----end submit");
        }

        //ConfigsPoJo configsPoJo = (ConfigsPoJo) SpringBootUtil.getBean(ConfigsPoJo.class);
        //Integer pathLockLength = configsPoJo.getPathLockLength();
        //System.out.println(pathLockLength);

        //RedisTemplate redisTemplate = (RedisTemplate) SpringBootUtil.getcBean("redisTemplate");
        //Set keys = redisTemplate.keys("*");
        //System.out.println(keys);
    }
}
