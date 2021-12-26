package com.utils.webservice.jws.client;

import javax.jws.WebService;

@WebService
public interface HelloWebService {
    public String sayHello(String name);
}
