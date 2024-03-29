package com.utils.mqtt.simple;

import com.utils.Test;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 消息订阅
 * @throws MqttException
 */
public class SubscribeSample {
    private static Logger logger = LoggerFactory.getLogger(SubscribeSample.class);
    public static void main(String[] args) throws MqttException {
        //String HOST = "tcp://ec2-161-189-119-183.cn-northwest-1.compute.amazonaws.com.cn";
        String HOST = "tcp://161.189.119.183:8089";
        //String HOST = "tcp://1.116.234.137:1883";
        //String TOPIC = "$SYS/brokers/+/clients/#";
        String TOPIC = "aaa/#";
        int qos = 2;
        String clientid = "aaaaaaaaaaaaaaaaaaaaa";
        try {
            // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            MqttClient client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName("abup");
            options.setPassword("abup2021".toCharArray());
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(100);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(600);
            // 设置回调函数
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    logger.info("connectionLost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    logger.info("111111111111----topic:" + topic);
                    //Thread.sleep(500);
                    logger.info("222222222222----Qos:" + message.getQos());
                    logger.info("333333333333----message content:" + new String(message.getPayload()));

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //logger.info("deliveryComplete---------" + token.isComplete());
                }

            });
            client.connect(options);
            int[] a = {0,1};
            String[] b = { "mqtt/up1111111111/11111111","aa"};
            //client.subscribe(b, a);
            //logger.info("11111111111111111111");
            //Thread.sleep(1000);
            client.subscribe(TOPIC, qos);
            //logger.info("2222222222222222222");
            //client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
