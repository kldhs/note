package com.utils.redis;

import java.util.Collection;


/**
 * @author xs
 * @date 2020/11/21 09:06
 */
public class RedisThread {
    public static void main(String[] args) {
        //往redis中写入数据
        new RedisUtil().setObject("111","222");
        //获取redis中所有键值
        Collection<String> keys = new RedisUtil().getKeys("*");
        System.out.println(keys);
    }
}
