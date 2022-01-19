package com.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(
        exclude= {DataSourceAutoConfiguration.class}
)
@EnableAsync
//开启定时任务
//@EnableScheduling
/**
 * 继承SpringBootServletInitializer可以使用外部tomcat，自己可以设置端口号，项目名
 */
public class UtilsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UtilsApplication.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(UtilsApplication.class, args);
        System.err.println(context.getEnvironment());
        Test.kafkaTest();


    }




}
