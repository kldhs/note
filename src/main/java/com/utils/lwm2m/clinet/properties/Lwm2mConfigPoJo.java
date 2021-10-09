package com.utils.lwm2m.clinet.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: iot-cloud-ota-coap
 * @description:
 * @author: miaomingwei
 * @create: 2020-09-14 14:51
 */
@Component
@ConfigurationProperties(prefix = "lwm2ma")
public class Lwm2mConfigPoJo {

    private  String midBegin;

    private  String host;

    public  String getMidBegin() {
        return midBegin;
    }

    public  void setMidBegin(String midBegin) {
        this.midBegin = midBegin;
    }

    public  String getHost() {
        return host;
    }

    public  void setHost(String host) {
        this.host = host;
    }
}