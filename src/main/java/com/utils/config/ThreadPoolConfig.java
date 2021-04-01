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
public class ThreadPoolConfig {
    @Value("${corePoolSize}")
    private Integer corePoolSize;

    @Value("${maxPoolSize}")
    private Integer startY;

    @Value("${queueCapacity}")
    private Integer startZ;

    @Value("${keepAlive}")
    private Integer lockLength;
}
