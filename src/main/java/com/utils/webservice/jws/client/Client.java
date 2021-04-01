package com.utils.webservice.jws.client;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    public static void main(String[] args) {
        try {
            //URL url = new URL(address location+"?wsdl");
            URL url = new URL("http://localhost:8080/Hello?wsdl");
            //QName qName = new QName(targetNamespace，service name);
            QName qName = new QName("http://xs.com/", "HelloServiceImplService");
            Service s = Service.create(url, qName);
            HelloWebService hs = s.getPort(HelloWebService.class);
            String str = hs.sayHello("hsaudfj");
            System.out.println(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
