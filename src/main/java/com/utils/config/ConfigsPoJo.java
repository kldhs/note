package com.utils.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xs
 * @date 2021/04/01 13:33
 */
@PropertySource(value = "classpath:/configs.properties")
@Component
@Getter
public class ConfigsPoJo {
    @Value("${logPath}")
    private String logPath;

    @Value("${loadExcel}")
    private String loadExcel;

    @Value("${unloadExcel}")
    private String unloadExcel;

    @Value("${horizontal}")
    private String horizontal;

    @Value("${trayToward}")
    private String trayToward;

    @Value("${startX}")
    private Integer startX;

    @Value("${startY}")
    private Integer startY;

    @Value("${startZ}")
    private Integer startZ;

    @Value("${lockLength}")
    private Integer lockLength;

    @Value("${up}")
    private Integer up;

    @Value("${down}")
    private Integer down;

    @Value("${left}")
    private Integer left;

    @Value("${right}")
    private Integer right;

    @Value("${pathLockLength}")
    private Integer pathLockLength;

    @Value("${remainPathLength}")
    private Integer remainPathLength;

    @Value("${overArea}")
    private String overArea;
}
