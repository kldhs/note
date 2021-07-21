package com.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync //启用定时任务
@EnableScheduling
/**
 * 继承SpringBootServletInitializer可以使用外部tomcat，自己可以设置端口号，项目名
 */
public class Application  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);





        //for (int i = 0; i <=10 ; i++) {
        //    AsyncService asyncService = (AsyncService) SpringBootUtil.getBean(AsyncService.class);
        //    System.err.println(i+"----start submit");
        //    //调用service层的任务
        //    asyncService.executeAsync();
        //    System.err.println(i+"----end submit");
        //}

        //ConfigsPoJo configsPoJo = (ConfigsPoJo) SpringBootUtil.getBean(ConfigsPoJo.class);
        //Integer pathLockLength = configsPoJo.getPathLockLength();
        //System.out.println(pathLockLength);

        //RedisTemplate redisTemplate = (RedisTemplate) SpringBootUtil.getcBean("redisTemplate");
        //Set keys = redisTemplate.keys("*");
        //System.out.println(keys);
    }
}
