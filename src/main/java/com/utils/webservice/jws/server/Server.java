package com.utils.webservice.jws.server;

import com.utils.timer.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Endpoint;

public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);
    public static void main(String[] args) {
        String address = "http://localhost:8080/Hello?wsdl";
        Endpoint.publish(address, new HelloServiceImpl());
        logger.info("发布webservice成功!");
    }
}
