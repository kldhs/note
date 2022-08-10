package com.utils.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.FunctionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
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

    public void aa(){
        DeviceReportInfoDayEntity dayEntity = new DeviceReportInfoDayEntity();
        dayEntity.setV0("0");
        dayEntity.setV1("1");
        dayEntity.setV2("2");
        dayEntity.setV3("3");
        dayEntity.setV4("4");
        dayEntity.setV5("4");

        //redis缓存设备最新消息
        String dayEntityValue = JSON.toJSONString(dayEntity);
        String dayEntityValue1 = JSONObject.toJSONString(dayEntity);
        stringRedisTemplate.opsForValue().set("aaaaaaaaaaaa:",dayEntityValue);

        stringRedisTemplate.opsForHash().increment("bbbbb", "name", 2);


        System.out.println(dayEntityValue);
    }


}
