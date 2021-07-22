package com.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws MqttException {
        SpringApplication.run(Application.class, args);
        //订阅主题，之后控制台打印消息。证明整合成功
        // MqttPushClient.getInstance().subscribe("topic1");
    }
}
