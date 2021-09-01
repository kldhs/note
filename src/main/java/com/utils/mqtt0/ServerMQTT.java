package com.utils.mqtt0;

/**
 * @author xs
 * @date 2021/8/24 10:49
 */

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Title:Server Description: 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
 */
public class ServerMQTT {

    // tcp://MQTT安装的服务器地址:MQTT定义的端口号
    public static final String HOST = "tcp://8.140.98.89:1883";
    // 定义一个主题
    public static final String TOPIC = "root/topic/testDx";
    // 定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String clientid = "server11";
    private MqttClient client;
    private MqttTopic topic11;
    private String userName = "admin";
    private String passWord = "admin";
    private MqttMessage message;

    /**
     * 构造函数
     *
     * @throws MqttException
     */
    public ServerMQTT() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }

    /**
     * 用来连接服务器
     */
    private void connect() {
        //1、设置连接属性
        MqttConnectOptions options = new MqttConnectOptions();
        //2、设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        // 这里设置为true表示每次连接到服务器都以新的身份连接；换而言之，设置为false时可以客户端可以接受离线消息
        options.setCleanSession(false);
        //3、用户名密码
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
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
            topic11 = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param topic
     * @param message
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! " + token.isComplete());
    }

    /**
     * 启动入口
     *
     * @param args
     * @throws MqttException
     */
    public static void main(String[] args) throws MqttException {
        ServerMQTT server = new ServerMQTT();
        server.message = new MqttMessage();
        server.message.setQos(1);
        server.message.setRetained(true);
        server.message.setPayload("hello,topic1日4".getBytes());
        server.publish(server.topic11, server.message);
        System.out.println(server.message.isRetained() + "------ratained状态");
    }
}