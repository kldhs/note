package com.utils.socket.server;



import com.utils.logutil.LogUtil;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

/**
 * @author xs
 */
public class SocketServerUtil {
    private static Hashtable<String, SocketServerUtil> socketServerUtilMap = new Hashtable<>();

    public int getPort() {
        return port;
    }

    private int port;

    /**
     * 私有化的构造函数
     * @param port
     */
    private SocketServerUtil(int port) {
        this.port = port;
    }

    /**
     * 从map中获取实例
     * @param port
     * @return
     */
    private static synchronized SocketServerUtil getSocketServerUtil(int port) {
        String key = getKey(port);
        if (socketServerUtilMap == null) {
            socketServerUtilMap = new Hashtable<>();
        }
        if (!socketServerUtilMap.containsKey(key)) {
            SocketServerUtil socketServerUtil = new SocketServerUtil(port);
            socketServerUtilMap.put(key, socketServerUtil);
            return socketServerUtil;
        } else {
            return socketServerUtilMap.get(key);
        }
    }

    /**
     * 获取socketServerMap的主键
     * @param port
     * @return
     */
    private static String getKey(int port) {
        return port + "";
    }

    /**
     * 开启指定端口的接收服务
     * @param port
     */
    public static void startServer(int port) {
        SocketServerUtil receiverUtil = getSocketServerUtil(port);
        receiverUtil.startServer();
    }

    /**
     * 开始接收
     */
    private void startServer() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                //设置连接超时时间
                serverSocket.setSoTimeout(5000);
                LogUtil.socketInfo("--------Socket服务端--------"+" 开启Socket服务端 "+"ip为：" + InetAddress.getLocalHost().getHostAddress() + " 端口号为：" + port);
                while (true) {
                    //LogUtil.socketInfo("--------Socket服务端--------"+" 等待连接中。。。。 Socket服务端已开启 "+" ip为：" + InetAddress.getLocalHost().getHostAddress() + " 端口号为：" + port);
                    Socket socket ;
                    try {
                        socket = serverSocket.accept();
                        //设置读取数据超时时间
                        socket.setSoTimeout(5000);
                    } catch (java.net.SocketTimeoutException e) {
                        //LogUtil.socketWarn("--------Socket服务端--------"+" 连接超时 Socket服务端重新进入accept方法等待客户端连接"+" ip为：" + InetAddress.getLocalHost().getHostAddress() + " 端口号为：" + port);
                        continue;
                    }
                    //每接收到一次消息，开启一个新的线程去处理。
                    new SocketServerThread(socket).start();
                    LogUtil.socketInfo("--------Socket服务端--------" + " 连接成功 "+"服务端 ip为：" + InetAddress.getLocalHost().getHostAddress() + " 端口号为：" + port
                    +"。 客户端 ip为：" + socket.getInetAddress() + " 端口号为：" + socket.getPort());
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}

