package com.utils.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author xs
 * @date 2022/1/10 9:09
 */
@Slf4j
@Service
public class RedisService {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    public void set(){
        //往redis中写入数据
        for (int i = 1; i <501 ; i++) {
            stringRedisTemplate.opsForValue().increment("bbbbbbbbbbbb");
            stringRedisTemplate.opsForValue().set("aaaaaaaaaaaa:"
                    +stringRedisTemplate.opsForValue().get("bbbbbbbbbbbb"),"asdfghjkl-----"+i);
        }
        int j = Integer.valueOf(stringRedisTemplate.opsForValue().get("bbbbbbbbbbbb"));
        for (int i = j-499; i <j+1 ; i++) {
            System.out.println(stringRedisTemplate.opsForValue().get("ccc:"
                    +i));
        }

        //获取redis中所有键值
        Collection<String> keys = stringRedisTemplate.keys("*");
        log.info(keys.toString());
    }
}
