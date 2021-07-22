package com.utils.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xs
 * @date 2021/04/01 13:33
 * 读取配置文件
 * @PropertySource 只能读取properties文件，如果想要读取yml文件需要复写相关方法
 * <p>
 * 方式1：
 * @PropertySource 指定文件 + @Value 指定属性 + .properties配置文件
 * 方式2：
 * @ConfigurationProperties 指定前缀 + .properties配置文件
 * 只要是.properties配置文件都可以
 * 方式3：
 * @ConfigurationProperties 指定前缀 + application.yml配置文件
 * 如果是.yml文件只能是application.yml命名的
 */

@Component
@PropertySource(value = "classpath:/configs.properties")
@ConfigurationProperties(prefix = "test")
public class ConfigsPoJo {
    @Value("${test.x}")
    private int x;

    @Value("${test.y}")
    private int y;

    @Value("${test.z}")
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
}
