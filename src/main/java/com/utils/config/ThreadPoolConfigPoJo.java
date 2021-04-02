package com.utils.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xs
 * @date 2021/04/01 13:33
 */
@PropertySource(value = "classpath:/threadPoolConfig.properties")
@Component
@Getter
public class ThreadPoolConfigPoJo {
    @Value("${corePoolSize}")
    private Integer corePoolSize;

    @Value("${maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${queueCapacity}")
    private Integer queueCapacity;

    @Value("${keepAlive}")
    private Integer keepAlive;

    @Value("${threadNamePrefix}")
    private String threadNamePrefix;
}
