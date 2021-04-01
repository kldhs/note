package com.utils.webservice.jws.server;

import javax.jws.WebService;

@WebService(targetNamespace = "http://xs.com/")
public class HelloServiceImpl implements HelloWebService {
    @Override
    public String sayHello(String name) {
        return "Hi!" + name;
    }
}
