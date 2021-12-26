package com.utils.config;

import com.utils.spring.SpringBootUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xs
 * @date 2021/04/01 13:33
 * 读取配置文件
 * @PropertySource 只能读取properties文件，如果想要读取yml文件需要复写相关方法
 * <p>
 * -------------方式1：
 * @PropertySource 指定文件 + @Value 指定属性 读取.properties配置文件
 * 例：
 * @PropertySource(value = "classpath:/configs.properties")
 * @Value("${test.x}") 。
 * <p>
 * -------------方式2：
 * @PropertySource 指定文件
 * @ConfigurationProperties 指定前缀 读取.properties配置文件
 * 只要是.properties配置文件都可以
 * 例：
 * @PropertySource(value = "classpath:/configs.properties")
 * @ConfigurationProperties(prefix = "test")
 * <p>
 * -------------方式3：
 * @ConfigurationProperties 指定前缀 读取.yml配置文件
 * .yml文件必须是被spring boot默认加载的.yml文件
 * 例：
 * @ConfigurationProperties(prefix = "test")spring boot默认加载的.yml文件中必须有以test为前缀的属性
 */
@Component
//@PropertySource(value = "classpath:/configs.properties")
@ConfigurationProperties(prefix = "test")
public class ConfigsPoJo {
    //@Value("${test.x}")
    private int x;

    //@Value("${test.y}")
    private int y;

    //@Value("${test.z}")
    private int z;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    //@Bean
    //public String aaa(String[] args) {
        //System.err.println("hiahia: " + new Date());
        //ConfigsPoJo aa = SpringBootUtil.getBean(ConfigsPoJo.class);
        //logger.info("111111111111111");
        //return null;
    //}
}
