package com.utils.mqtt;

/**
 * @author xs
 * @date 2021/8/24 10:54
 */

import com.utils.mqtt.simple.PublishSample;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttClientCallback implements MqttCallback {
    private static Logger logger = LoggerFactory.getLogger(MqttClientCallback.class);
    /**
     * mqtt客户端对象
     */
    private MqttClient mqttClient;

    public MqttClientCallback(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void connectionLost(Throwable cause) {
        MqttClientService.mqttClientTable.remove(mqttClient.getClientId());
        System.err.println("clientid : " + mqttClient.getClientId() + " this connection has been disconnected");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("delivery result : " + token.isComplete() + " , clientid : " + mqttClient.getClientId());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        logger.info("receive message -> This clientid : " + mqttClient.getClientId() + " . receive -> topic : " + topic
                + " , qos : " + message.getQos() + " , retained : " + message.isRetained() + " , message : " + new String(message.getPayload()));
    }
}