package com.utils.mqtt;

import com.utils.mqtt1.PushCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.ScheduledExecutorService;

/**
 * @author xs
 * @date 2021/9/3 9:55
 * 订阅者
 */
public class Subscriber {
    public static final String HOST = "tcp://8.140.98.89:1883";
    public static final String TOPIC = "root/topic/testDx";
    private static final String clientid = "server11";
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "admin";
    private String passWord = "admin";

    private void start() {
        try {
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);
            client.setCallback(new PushCallback());
            MqttTopic topic = client.getTopic(TOPIC);
            options.setWill(topic, "close".getBytes(), 2, true);
            client.connect(options);
            int[] Qos = {1};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, Qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Subscriber subscriber = new Subscriber();
        subscriber.start();
    }
}
