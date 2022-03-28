package com.utils.mqtt;

/**
 * @author xs
 * @date 2021/8/24 10:54
 */

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttAsyncClientCallback implements MqttCallback {
    private static Logger logger = LoggerFactory.getLogger(MqttAsyncClientCallback.class);
    /**
     * mqtt客户端对象
     */
    private MqttAsyncClient mqttAsyncClient;

    public MqttAsyncClientCallback(MqttAsyncClient mqttAsyncClient) {
        this.mqttAsyncClient = mqttAsyncClient;
    }

    @Override
    public void connectionLost(Throwable cause) {
        MqttClientService.mqttClientTable.remove(mqttAsyncClient.getClientId());
        System.err.println("clientid : " + mqttAsyncClient.getClientId() + " this connection has been disconnected");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        logger.info("delivery result : " + token.isComplete() + " , clientid : " + mqttAsyncClient.getClientId());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        logger.info("receive message -> This clientid : " + mqttAsyncClient.getClientId() + " . receive -> topic : " + topic
                + " , qos : " + message.getQos() + " , retained : " + message.isRetained() + " , message : " + new String(message.getPayload()));
    }
}