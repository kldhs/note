package com.utils.socket.client;

import com.utils.serialport.SerialPortUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author xs
 * @
 */
public class SocketLongConnectClient {
    private static Logger logger = LoggerFactory.getLogger(SocketLongConnectClient.class);
    /**
     * SocketLongConnectClient对象Map，key
     */
    public static HashMap<String, SocketLongConnectClient> socketLongConnectClientMap = new HashMap<String, SocketLongConnectClient>();
    String ip;
    int port;
    Socket socket = null;
    private InputStream in = null;
    private OutputStream out = null;

    private SocketLongConnectClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static SocketLongConnectClient getSocketLongConnectClient(String ip, int port) {
        SocketLongConnectClient socketLongConnectClient = null;
        socketLongConnectClient = socketLongConnectClientMap.get(ip + ":" + port);
        if (socketLongConnectClient == null) {
            socketLongConnectClient = new SocketLongConnectClient(ip, port);
            socketLongConnectClientMap.put(ip + ":" + port, socketLongConnectClient);
        }
        return socketLongConnectClient;
    }

    private boolean connect() {
        if (socket == null || in == null || out == null || !socket.isConnected() || socket.isClosed()) {
            try {
                terminate();
                socket = new Socket();
                //设置连接请求超时时间2s
                socket.connect(new InetSocketAddress(ip, port), 3000);
                //设置读取数据超时时间
                socket.setSoTimeout(10000);
                in = socket.getInputStream();
                out = socket.getOutputStream();
                return true;
            } catch (Exception e) {
                terminate();
                return false;
            }
        } else {
            return true;
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
            if(in.read(bytes)<0){
                //terminate();
            }
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            terminate();
            connect();
        }
        return null;
    }

    /**
     * r
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

    public static String sendMessageBySocketLongConnectClient(String ip, int port, String message) {
        SocketLongConnectClient socketLongConnectClient = getSocketLongConnectClient(ip, port);
        while (!socketLongConnectClient.connect()) {
            System.err.println("socket客户端长连接失败，ip:" + ip + ",端口：" + port);
            try {
                Thread.sleep(51000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes = socketLongConnectClient.send(message.getBytes());
        if (bytes != null) {
            return new String(bytes);
        } else {
            return "null";
        }
    }

    public static void main(String[] args) {
        while (true) {
            String aa = SocketLongConnectClient.sendMessageBySocketLongConnectClient("127.0.0.1", 9000, "1111");
            logger.info(aa);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
