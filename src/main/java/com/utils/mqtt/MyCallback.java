package com.utils.mqtt;

/**
 * @author xs
 * @date 2021/8/24 10:54
 */
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyCallback implements MqttCallback {
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("连接断开，可以做重连");
    }
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("推送合理呀deliveryComplete---------" + token.isComplete());
    }
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
    }
}