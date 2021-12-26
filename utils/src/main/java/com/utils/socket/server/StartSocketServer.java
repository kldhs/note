package com.utils.socket.server;

/**
 * @author xs
 * @date 2019/11/19 13:13
 */
public class StartSocketServer {
    public static void main(String[] args)   {
        int port = 1111;
        SocketServerUtil.startServer(port);
    }
}
