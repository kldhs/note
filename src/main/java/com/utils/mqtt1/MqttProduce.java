package com.utils.mqtt1;

import com.utils.mqtt0.PushCallback;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

/**
 * @author xs
 * @date 2021/8/31 9:59
 */
@Component
public class MqttProduce {

    private MqttProduce() {
    }
    private static String MqUrl="tcp://127.0.0.1:61616";
    private static String MqClinetId="server11";

    private static String TOPIC="topic01";
    private static String MESSAGE="1111111111";
    private static MqttClient client;
    private static MqttTopic topic;
    private static MqttMessage message;

    /**
     * Mqtt-推送
     *
     * @param top Topic
     * @param msg Message
     * @throws MqttException
     */
    public static void doPush(String top, String msg) throws MqttException {
        TOPIC = top;
        MESSAGE = msg;
        client = new MqttClient(MqUrl, MqClinetId, new MemoryPersistence());
        connect();
        message = new MqttMessage();
        message.setQos(2);
        //设置是否在服务器中保存消息体
        message.setRetained(true);
        //设置消息的内容
        message.setPayload(MESSAGE.getBytes());
        //发布
        publish(topic, message);
    }

    private static void connect() {
        //1、设置连接属性
        MqttConnectOptions options = new MqttConnectOptions();
        //2、设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接；换而言之，设置为false时可以客户端可以接受离线消息
        options.setCleanSession(false);
        //3、用户名密码
        //options.setUserName(MqUserName);
        //options.setPassword(MqPassword.toCharArray());
        //4、设置超时时间
        options.setConnectionTimeout(10);
        //5、设置回话心跳时间
        options.setKeepAliveInterval(20);
        try {
            //6、设置回调类
            client.setCallback(new PushCallback());
            //7、连接
            client.connect(options);
            //8、获取activeMQ上名为TOPIC的topic
            topic = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
        // 发布的方法
        MqttDeliveryToken token = topic.publish(message);
        // 发布
        token.waitForCompletion();
        System.out.println("message is published completely! " + token.isComplete());
    }

    public static void main(String[] args)   {
        try {
            MqttProduce.doPush("111", "111");
            System.out.println("222222222222");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
