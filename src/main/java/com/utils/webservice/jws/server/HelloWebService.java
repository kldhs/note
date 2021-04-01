package com.utils.webservice.jws.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HelloWebService {
    @WebMethod
    String sayHello(String name);
}
