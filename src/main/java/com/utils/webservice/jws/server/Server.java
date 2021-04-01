package com.utils.webservice.jws.server;

import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        String address = "http://localhost:8080/Hello?wsdl";
        Endpoint.publish(address, new HelloServiceImpl());
        System.out.println("发布webservice成功!");
    }
}
