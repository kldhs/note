package com.utils.webservice.jws.client;

import com.utils.webservice.jws.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

public class Client {
    private static Logger logger = LoggerFactory.getLogger(Client.class);
    public static void main(String[] args) {
        try {
            //URL url = new URL(address location+"?wsdl");
            URL url = new URL("http://localhost:8080/Hello?wsdl");
            //QName qName = new QName(targetNamespace，service name);
            QName qName = new QName("http://xs.com/", "HelloServiceImplService");
            Service s = Service.create(url, qName);
            HelloWebService hs = s.getPort(HelloWebService.class);
            String str = hs.sayHello("hsaudfj");
            logger.info(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
