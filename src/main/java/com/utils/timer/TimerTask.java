package com.utils.timer;

import com.utils.config.ConfigsPoJo;
import com.utils.spring.SpringBootUtil;
import com.utils.threadpool.noexecutorsandspring.MyThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xs
 * @date 2021/7/21 16:53
 * 定时器
 */
//标注一个类为Spring容器的Bean
@Component
@PropertySource("classpath:timer-task.properties")
public class TimerTask {
    private static Logger logger = LoggerFactory.getLogger(TimerTask.class);
    @Scheduled(cron = "${taskTime}")
    public void tokenPub() {
        try {
            System.err.println("hiahia: " + new Date());
            ConfigsPoJo aa = SpringBootUtil.getBean(ConfigsPoJo.class);
            logger.info("TimerTask: 定时器任务");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
