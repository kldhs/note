package com.utils;

import com.utils.kafka.KafkaProducerService;
import com.utils.lwm2m.clinet.client.Lwm2mClient;
import com.utils.lwm2m.clinet.properties.Lwm2mConfigPoJo;
import com.utils.mqtt.MqttClientService;
import com.utils.mysql.model.entity.DeviceReportInfo;
//import com.utils.mysql.service.DeviceReportInfoService;
import com.utils.redis.RedisService;
import com.utils.spring.SpringBootUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.leshan.core.model.InvalidDDFFileException;
import org.eclipse.leshan.core.model.InvalidModelException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xs
 * @date 2021/11/4 18:18
 * 测试方法
 */
@Slf4j
@Component
public class Test {

    public static void asyncTest() {
        for (int i = 0; i <= 100; i++) {

            System.err.println(i + "----end submit");
        }
    }

    /**
     * mqtt 测试方法
     */
    public  void mqttTest()  {

        //MqttClientService bean = SpringBootUtil.getBean(MqttClientService.class);
        //try {
        //    bean.publish("7D0109000000001900000000298D43C16EE11500310001FFFF01298D43C10739476E1" +
        //                    "1F9C96C0000000019150B06C70B970DC711D08201CC000016CA00000000E2FFFF0B0000E104153F" +
        //                    "00000A0102030400010102030405010203040000E60021FF0102030401020304010203040102030" +
        //                    "401020304010203040102030401020304E601020102E602049370BF41E603480000007F000003E8" +
        //                    "01F401F4000111000000054FFF8986000000000000000170B5E83A750701CC000001813A0700010" +
        //                    "0010000000101000000000000000000000000000000000000E6054400427D010863584050038461" +
        //                    "000228AB29946E30002E0000FFFF0E28AB29940739477711F9C9900000000019150B00000B97000" +
        //                    "000008201CC000016CA00000000E2FFFFE60A05047D01086E", "0863584050038461_0863584050038461",
        //            "root/topic/testDx", 2, true);
        //} catch (MqttException e) {
        //    e.printStackTrace();
        //}
        //bean.publish("asdfghjklasdfghjkl", "asdfghjklasdfghjkl", "root/topic/testDx1", 2, true);
        //int[] Qos = {2};
        //String[] topic1 = {"root/topic/testDx1"};
        //String a = UUID.randomUUID().toString().replaceAll("-", "");
        //bean.subscribe(null, a, topic1, Qos);
    }



}
