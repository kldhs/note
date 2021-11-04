package com.utils;

import com.utils.lwm2m.clinet.client.Lwm2mClient;
import com.utils.lwm2m.clinet.properties.Lwm2mConfigPoJo;
import com.utils.mqtt.MqttClientService;
import com.utils.spring.SpringBootUtil;
import org.eclipse.leshan.core.model.InvalidDDFFileException;
import org.eclipse.leshan.core.model.InvalidModelException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author xs
 * @date 2021/11/4 18:18
 * 测试方法
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);
    /**
     * mqtt 测试方法
     */
    public static void mqttTest() {

        MqttClientService bean = SpringBootUtil.getBean(MqttClientService.class);
        try {
            bean.publish("7D0109000000001900000000298D43C16EE11500310001FFFF01298D43C10739476E1" +
                            "1F9C96C0000000019150B06C70B970DC711D08201CC000016CA00000000E2FFFF0B0000E104153F" +
                            "00000A0102030400010102030405010203040000E60021FF0102030401020304010203040102030" +
                            "401020304010203040102030401020304E601020102E602049370BF41E603480000007F000003E8" +
                            "01F401F4000111000000054FFF8986000000000000000170B5E83A750701CC000001813A0700010" +
                            "0010000000101000000000000000000000000000000000000E6054400427D010863584050038461" +
                            "000228AB29946E30002E0000FFFF0E28AB29940739477711F9C9900000000019150B00000B97000" +
                            "000008201CC000016CA00000000E2FFFFE60A05047D01086E", "0863584050038461_0863584050038461",
                    "root/topic/testDx", 2, true);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        //bean.publish("asdfghjklasdfghjkl", "asdfghjklasdfghjkl", "root/topic/testDx1", 2, true);
        int[] Qos = {2, 2};
        String[] topic1 = {"root/topic/testDx1", "root/topic/testDx"};
        String a = "server332";
        bean.subscribe("root/topic/testDx1", a, topic1, Qos);
    }

    /**
     * lwm2m 客户端，测试方法
     */
    public static void lwm2mClientTest() throws InvalidModelException, InvalidDDFFileException, IOException {
        Lwm2mConfigPoJo lwm2MConfigPoJo = SpringBootUtil.getBean(Lwm2mConfigPoJo.class);
        new Lwm2mClient().createLwm2mClient(lwm2MConfigPoJo.getEndpoint());
    }

    /**
     * lslf4j 日志输出，测试方法
     */
    public static void lslf4jTest() {
        logger.error("1111111111111111111111111");
        logger.info("1111111111111111111111111");
        logger.info("1111111111111111111111111");
    }
}
