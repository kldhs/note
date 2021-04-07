package com.utils.webapi.receiveFromWms;

import com.suray.enums.SysParam;
import com.suray.route.util.LogUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收服务
 */
public class AcceptFromWmsServer {

    public static void startServer() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    ServerSocket s = new ServerSocket(SysParam.FromTOWmsPort, 3);    // 创建一个监听8000端口的服务器Socket
                    while (true) {
                            LogUtil.info("等待WMS连接" + System.currentTimeMillis());
                            socket = s.accept();
                            LogUtil.info("与WMS连接已建立。端口号：" + socket.getPort() + "。" + System.currentTimeMillis());
                            new AcceptFromWmsThread(socket).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
