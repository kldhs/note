package com.utils.mqtt;


import com.utils.mqtt1.PushCallback;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xs
 * @date 2021/9/2 17:59
 * 发布者
 */
public class Publisher {

    @Autowired
    MqttConfig mqttConfig;
    private  final String HOST = "tcp://"+mqttConfig.getIp()+":"+mqttConfig.getPort();
    private String userName = mqttConfig.getUserName();
    private String passWord = mqttConfig.getPassWord();

    private  final String TOPIC = "root/topic/testDx";
    private  final String clientid = "server11";
    private MqttClient mqttClient;
    private MqttTopic mqttTopic;


    public Publisher() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(userName);
        mqttConnectOptions.setPassword(passWord.toCharArray());
        mqttConnectOptions.setConnectionTimeout(10);
        mqttConnectOptions.setKeepAliveInterval(20);
        try {
            mqttClient = new MqttClient(HOST, clientid, new MemoryPersistence());
            mqttClient.setCallback(new PushCallback());
            mqttClient.connect(mqttConnectOptions);
            mqttTopic = mqttClient.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void publish(int qos,boolean retained, String message) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);
        mqttMessage.setPayload(message.getBytes());
        MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
        token.waitForCompletion();
    }

    /**
     * 启动入口
     *
     * @param args
     * @throws MqttException
     */
    public static void main(String[] args) throws MqttException {
        Publisher publisher = new Publisher();
        publisher.publish(1,true,"aaaaaaaaaaaaaaaaaa");
    }
}
