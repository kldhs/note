package com.utils.mqtt.simple;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息发布
 *
 * @author YT
 * 2020-05-08
 */
public class PublishSample {
    private static Logger logger = LoggerFactory.getLogger(PublishSample.class);
    public static void main(String[] args) {

        String topic = "mqtt/dn/1646189970/1";
        String content = "";
        int qos = 1;
        String broker = "tcp://127.0.0.1:1883";
            String clientId = "pushPublisherdota5AESDGfhtrdy";
        // 内存存储
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            // 创建客户端
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            // 创建链接参数
            MqttConnectOptions connOpts = new MqttConnectOptions();
            // 在重新启动和重新连接时记住状态
            connOpts.setCleanSession(false);
            connOpts.setUserName("abup");
            connOpts.setPassword("abup2021".toCharArray());
            // 建立连接
            sampleClient.connect(connOpts);
            // 创建消息
            MqttMessage message = new MqttMessage(content.getBytes());
            // 设置消息的服务质量
            message.setQos(qos);
            message.setRetained(true);
            // 发布消息
            sampleClient.publish(topic, message);
            String TOPIC = "/#";
            int qos1 = 2;
            sampleClient.subscribe(TOPIC, qos);
            // 断开连接
            sampleClient.disconnect();
            // 关闭客户端
            sampleClient.close();
        } catch (MqttException me) {
            logger.info("reason " + me.getReasonCode());
            logger.info("msg " + me.getMessage());
            logger.info("loc " + me.getLocalizedMessage());
            logger.info("cause " + me.getCause());
            logger.info("excep " + me);
            me.printStackTrace();
        }
    }
}
