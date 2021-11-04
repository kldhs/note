package com.utils.lwm2m.server;

import org.eclipse.leshan.server.californium.LeshanServer;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;

/**
 * @author xs
 * @date 2021/11/4 8:50
 */
public class Lwm2mServer {

    public static void main(String[] args) {
        LeshanServerBuilder builder = new LeshanServerBuilder();
        LeshanServer server = builder.build();
        server.start();
    }

}
