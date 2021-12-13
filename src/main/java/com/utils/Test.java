package com.utils;

import com.alibaba.fastjson.JSON;
import com.utils.aop.UserAction;
import com.utils.lwm2m.clinet.client.Lwm2mClient;
import com.utils.lwm2m.clinet.properties.Lwm2mConfigPoJo;
import com.utils.mqtt.MqttClientService;
import com.utils.spring.SpringBootUtil;
import com.utils.threadpool.executorsandspring.AsyncService;
import org.eclipse.leshan.core.model.InvalidDDFFileException;
import org.eclipse.leshan.core.model.InvalidModelException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xs
 * @date 2021/11/4 18:18
 * 测试方法
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);
    private static Logger logger1 = LoggerFactory.getLogger("login");
    private static Logger logger2 = LoggerFactory.getLogger("register");
    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;
    /**
     * mqtt 测试方法
     */
    public static void mqttTest() {

        MqttClientService bean = SpringBootUtil.getBean(MqttClientService.class);
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
        int[] Qos = {2};
        String[] topic1 = {"root/topic/testDx1"};
        String a = UUID.randomUUID().toString().replaceAll("-", "");
        bean.subscribe(null, a, topic1, Qos);
    }

    /**
     * lwm2m 客户端，测试方法
     */
    public static void lwm2mClientTest() throws InvalidModelException, InvalidDDFFileException, IOException {
        Lwm2mConfigPoJo lwm2MConfigPoJo = SpringBootUtil.getBean(Lwm2mConfigPoJo.class);
        new Lwm2mClient().createLwm2mClient(lwm2MConfigPoJo.getEndpoint());
    }

    /**
     * 异步线程池，测试方法
     */
    public static void asyncTest() {
        for (int i = 0; i <= 100; i++) {
            AsyncService asyncService = (AsyncService) SpringBootUtil.getBean(AsyncService.class);
            System.err.println(i + "----start submit");
            //调用service层的任务
            asyncService.executeAsync();
            System.err.println(i + "----end submit");
        }
    }

    /**
     * lslf4j 日志输出，测试方法
     */
    public static void slf4jTest() {
        logger.trace("1111111111111111111111111");
        logger.debug("2222222222222222222222222");
        logger.info("3333333333333333333333333");
        logger.warn("44444444444444444444444444");
        logger.error("55555555555555555555555555");
        logger1.trace("--------------1111111111111111111111111");
        logger1.debug("--------------2222222222222222222222222");
        logger1.info("--------------3333333333333333333333333");
        logger1.warn("--------------44444444444444444444444444");
        logger1.error("--------------55555555555555555555555555");
        logger2.trace("++++++++++++++1111111111111111111111111");
        logger2.debug("++++++++++++++2222222222222222222222222");
        logger2.info("++++++++++++++3333333333333333333333333");
        logger2.warn("++++++++++++++44444444444444444444444444");
        logger2.error("++++++++++++++55555555555555555555555555");

    }

    /**
     * lslf4j 日志输出，测试方法
     */
    public static void aopTest() {
        UserAction bean = SpringBootUtil.getBean(UserAction.class);
        bean.login();

    }

    /**
     * kafka 测试方法
     */
    public static void kafkaTest() {
        KafkaTemplate<String, Object> kafkaTemplate = SpringBootUtil.getBean(KafkaTemplate.class);
        kafkaTemplate.send("pushDetail_update", "pushDetail_update", "qwsdfgasdf");
    }

}
