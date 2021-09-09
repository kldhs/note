package com.utils.mqtt;

import com.sun.deploy.util.StringUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.stream.Collectors;

/**
 * @author xs
 * @date 2021/9/6 10:51
 */
@Configuration
public class MqttClientService {
    @Autowired
    MqttConfigPoJo mqttConfigPoJo;

    /**
     * MqttClient对象
     */
    public static Hashtable<String, MqttClient> mqttClientTable = new Hashtable<>();
    /**
     * 连接设置
     */
    MqttConnectOptions mqttConnectOptions;

    /**
     * 连接选项设置
     */
    public void creatMqttClientService() {
        if (mqttConnectOptions == null) {
            mqttConnectOptions = new MqttConnectOptions();
            // false:不清空session,服务器会保留客户端的连接记录，可以接收离线期的消息
            // true:清空session,服务器不会保留客户端的连接记录，新的连接会创建新Session，不能接收离线期的消息
            mqttConnectOptions.setCleanSession(this.mqttConfigPoJo.isCleanSession());
            mqttConnectOptions.setUserName(this.mqttConfigPoJo.getUserName());
            mqttConnectOptions.setPassword(this.mqttConfigPoJo.getPassWord().toCharArray());
            //设置连接超时时间
            mqttConnectOptions.setConnectionTimeout(this.mqttConfigPoJo.getConnectionTimeout());
            // 设置会话心跳时间，服务器会每隔一段时间向客户端发送个消息判断客户
            // 端是否在线，但这个方法并没有重连的机制（单位为秒）
            mqttConnectOptions.setKeepAliveInterval(this.mqttConfigPoJo.getKeepAliveInterval());
        }
    }

    /**
     * 连接
     *
     * @param clientid
     * @return
     * @throws MqttException
     */
    MqttClient getMqttClient(String clientid, String topic) throws MqttException {
        MqttClient mqttClient;
        if (MqttClientService.mqttClientTable.get(clientid) == null) {
            //如果该 clientid 对应的 MqttClient 不存在，则创建MqttClient对象
            mqttClient = new MqttClient("tcp://" + this.mqttConfigPoJo.getIp() + ":" + this.mqttConfigPoJo.getPort(),
                    clientid, new MemoryPersistence());
            creatMqttClientService();
            mqttClient.setCallback(new MqttClientCallback(mqttClient));
            MqttClientService.mqttClientTable.put(clientid, mqttClient);
            //设置遗愿消息，当订阅此topic的订阅者第一次上线，并且该topic的发布者离线时，订阅者会收到此遗愿消息
            if (topic != null) {
                mqttConnectOptions.setWill(mqttClient.getTopic(topic),
                        ("MqttClient : " + mqttClient + "is offline").getBytes(), 2, true);
            }
            mqttClient.connect(mqttConnectOptions);
        } else {
            //如果该 clientid 对应的 MqttClient 存在，则直接从HashTable中获取MqttClient对象
            mqttClient = MqttClientService.mqttClientTable.get(clientid);
        }
        return mqttClient;
    }

    /**
     * 推送
     *
     * @param message  需要推送的 消息
     * @param clientid 当前连接的 客户端id
     * @param topic    推送的 主题
     * @param qos      服务质量
     *                 QoS0，At most once，至多一次；
     *                 QoS1，At least once，至少一次；
     *                 QoS2，Exactly once，确保只有一次。
     * @param retained 设置是否保留消息，设置为true：如果没有订阅者消费此条消息，则mqtt服务端会一直保留此条消息。
     *                 该主题订阅者一旦上线，该消息就会被推送。设置为false：服务端不会保留此消息。
     * @throws MqttException
     */
    public void publish(String message, String clientid, String topic, int qos, boolean retained) throws MqttException {
        MqttClient mqttClient = getMqttClient(clientid, topic);
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);
        mqttMessage.setPayload(message.getBytes());
        MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
        token.waitForCompletion();
        System.out.println("publish message -> This clientid : " + mqttClient.getClientId() + " , topic : " + topic
                + " , retained : " + retained + " , qos : " + qos + " , message : " + message);
    }

    /**
     * 订阅
     *
     * @param clientid
     * @param topic
     * @param qos
     */
    public void subscribe(String willMessageTopic, String clientid, String[] topic, int[] qos) {
        try {
            MqttClient mqttClient = getMqttClient(clientid, willMessageTopic);
            mqttClient.subscribe(topic, qos);
            System.out.println("subscribe message -> This clientid : " + clientid
                    + " , topic : "+ Arrays.asList(topic).stream().map(String::valueOf).collect(Collectors.joining("、"))
                    + " , qos : " + Arrays.stream(qos).boxed().map(i -> i.toString()) .collect(Collectors.joining("、")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开客户端连接
     *
     * @param clientid
     */
    public void disconnect(String clientid) throws MqttException {
        if (MqttClientService.mqttClientTable.get(clientid) != null) {
            MqttClient mqttClient = MqttClientService.mqttClientTable.get(clientid);
            mqttClient.disconnect();
        } else {
            System.err.println("clientid : " + clientid + " this connection does not exist");
        }
    }

}
