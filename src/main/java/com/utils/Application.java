package com.utils;

import com.utils.mqtt.MqttClientService;
import com.utils.spring.SpringBootUtil;
import org.eclipse.paho.client.mqttv3.MqttException;
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
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws MqttException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        MqttClientService bean = SpringBootUtil.getBean(MqttClientService.class);
        bean.publish("7D0109000000001900000000298D43C16EE11500310001FFFF01298D43C10739476E1" +
                "1F9C96C0000000019150B06C70B970DC711D08201CC000016CA00000000E2FFFF0B0000E104153F" +
                "00000A0102030400010102030405010203040000E60021FF0102030401020304010203040102030" +
                "401020304010203040102030401020304E601020102E602049370BF41E603480000007F000003E8" +
                "01F401F4000111000000054FFF8986000000000000000170B5E83A750701CC000001813A0700010" +
                "0010000000101000000000000000000000000000000000000E6054400427D010863584050038461" +
                "000228AB29946E30002E0000FFFF0E28AB29940739477711F9C9900000000019150B00000B97000" +
                "000008201CC000016CA00000000E2FFFFE60A05047D01086E", "server1", "root/topic/testDx", 2, true);
        bean.publish("rdshgasdrhwdthwg", "server1", "root/topic/testDx1", 2, true);
        int[] Qos = {2,2};
        String[] topic1 = {"root/topic/testDx1","root/topic/testDx"};
        String a = "server332";
        bean.subscribe("root/topic/testDx1",a,topic1, Qos);
        //bean.setWillMessage("AFDagfSRAGHSDTJHSRTJDTYJKSRTFGASDFHQETH", a,"root/topic/testDx", 2, true);
    }
}
