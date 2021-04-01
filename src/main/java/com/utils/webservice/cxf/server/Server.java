//package com.utils.webservice.cxf.server;
//
//import org.apache.cxf.frontend.ServerFactoryBean;
//
//public class Server {
//    public Server() throws Exception{
//        HelloWordImpl hw = new HelloWordImpl();
//        ServerFactoryBean sfb = new ServerFactoryBean();
//        sfb.setServiceClass(HellowWord.class);
//        sfb.setServiceBean(hw);
//        sfb.setAddress("http://localhost:9000/Hello");
//        sfb.create();
//    }
//
//    public static void main(String[] args) throws Exception{
//        new Server();
//        System.out.println("server start ...");
//        //Thread.sleep(10*1000);
//        //System.out.println("server exit ...");
//        //System.exit(0);
//    }
//}
