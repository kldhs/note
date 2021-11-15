package com.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


/**
 * @author xs
 * @date 2020/11/21 09:06
 */

public class RedisThread {
    private static Logger logger= LoggerFactory.getLogger(RedisThread.class);
    public static void main(String[] args) {
        //往redis中写入数据
        new RedisUtil().setObject("111","222");
        //获取redis中所有键值
        Collection<String> keys = new RedisUtil().getKeys("*");
        logger.info(keys.toString());
    }
}
