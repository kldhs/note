package com.utils.socket.server;

import com.utils.excelutil.ExcelUtil;
import com.utils.logutil.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;


class SocketServerThread extends Thread {
    private static Logger logger = LoggerFactory.getLogger(SocketServerThread.class);
    private Socket socket;
    DataInputStream in = null;
    OutputStream out = null;

    public SocketServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //inCopy = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = socket.getOutputStream();
            String ipAdd = socket.getInetAddress().toString().substring(1);
            String localPort = String.valueOf(socket.getLocalPort());
            String port = String.valueOf(socket.getPort());
            while (true) {
                byte[] bytes = new byte[1024];
                int readLength =in.read(bytes);
                if ( readLength> 0) {
                    String messageFromClient = new String(bytes);
                    LogUtil.socketInfo("--------Socket服务端--------" + "ip为:" + InetAddress.getLocalHost().getHostAddress() + " 端口为:" + localPort + "的服务端 接收到ip为:" + InetAddress.getLocalHost().getHostAddress() + " 端口为:" + port + "的客户端发送的数据为:" + "\r\n" + messageFromClient);
                    out.write(("Hello 我是服务端，已接收到你的数据为:"+messageFromClient).getBytes());
                    out.flush();
                    LogUtil.socketInfo("--------Socket服务端--------" + "ip为:" + InetAddress.getLocalHost().getHostAddress() + " 端口为:" + localPort + "的服务端 向ip为:" + InetAddress.getLocalHost().getHostAddress() + " 端口为:" + port + "的客户端返回的数据为:" + "\r\n" + "Hello 我是服务端，已接收到你的数据为:"+messageFromClient);
                } else if(readLength==0){
                    logger.info("readLength==0");
                }else if(readLength<0){
                    logger.info("readLength<0");
                }
            }
    } catch( Exception e){
        e.printStackTrace();
        terminate();
    }
}

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
