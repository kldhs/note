package com.utils.socket.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author xs
 * @date socket客户端短连接
 */
public class SocketShortConnectClient {
    /**
     * SocketShortConnectClient对象Map，key
     */
    public static HashMap<String, SocketShortConnectClient> socketShortConnectClientMap = new HashMap<String, SocketShortConnectClient>();
    String ip;
    int port;
    Socket socket = null;
    private InputStream in = null;
    private OutputStream out = null;

    private SocketShortConnectClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static SocketShortConnectClient getSocketShortConnectClient(String ip, int port) {
        SocketShortConnectClient socketShortConnectClient = null;
        socketShortConnectClient = socketShortConnectClientMap.get(ip + ":" + port);
        if (socketShortConnectClient == null) {
            socketShortConnectClient = new SocketShortConnectClient(ip, port);
            socketShortConnectClientMap.put(ip + ":" + port, socketShortConnectClient);
        }
        return socketShortConnectClient;
    }

    private void connect() {
        if (socket == null || in == null || out == null || !socket.isConnected() || socket.isClosed()) {
            try {
                socket = new Socket();
                //设置连接请求超时时间2s
                socket.connect(new InetSocketAddress(ip, port), 2000);
                //超时时间设置为0被解释为无穷大。此处超时时间设置为10s
                socket.setSoTimeout(10000);
                in = socket.getInputStream();
            } catch (Exception e) {
                terminate();
            }
        }
    }

    /**
     *
     */
    private synchronized byte[] send(byte[] bs) {
        try {
            byte[] bytes = new byte[1024];
            out.write(bs);
            out.flush();
            if (in.available() > 0) {
                in.read(bytes);
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            terminate();
        }
        return null;
    }

    /**
     *
     */
    private void terminate() {
        try {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            in = null;
            out = null;
            socket = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
