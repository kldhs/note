package com.utils;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.utils.spring.SpringBootUtil;
import lombok.SneakyThrows;
import org.eclipse.leshan.core.model.InvalidDDFFileException;
import org.eclipse.leshan.core.model.InvalidModelException;
import org.eclipse.paho.client.mqttv3.MqttException;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication(exclude = {
        //DataSourceAutoConfiguration.class,
        //DataSourceTransactionManagerAutoConfiguration.class,
        //DruidDataSourceAutoConfigure.class ,
        //HibernateJpaAutoConfiguration.class
})
//@MapperScan({"com.utils.mysql.mapper"})
@EnableAsync
//开启定时任务
@EnableScheduling
/**
 * 继承SpringBootServletInitializer可以使用外部tomcat，自己可以设置端口号，项目名
 */
public class FrameworkApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FrameworkApplication.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FrameworkApplication.class, args);
        Test test = SpringBootUtil.getBean(Test.class);
        test.mysqlTest();
    }




}
