package com.utils.serialport;

import com.utils.aop.LogIntercept;
import gnu.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author xs
 * @date 2020/08/28 11:20
 * 监听的方式从串口读取数据
 */
public class SerialPortListenerUtil extends Thread implements SerialPortEventListener {
    private static Logger logger = LoggerFactory.getLogger(SerialPortListenerUtil.class);
    /**
     * 串口名称
     */
    private String portName;

    /**
     * 波特率
     */
    private int baudRate;

    /**
     * 校验位
     */
    private int checkoutBit;

    /**
     * 数据位
     */
    private int dataBit;
    /**
     * 停止位
     */
    private int stopBit;

    CommPortIdentifier commPortIdentifier = null;
    SerialPort serialPort = null;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    private BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();

    public SerialPortListenerUtil(String portName, int baudRate, int checkoutBit, int dataBit, int stopBit) {
        this.portName = portName;
        this.baudRate = baudRate;
        this.checkoutBit = checkoutBit;
        this.dataBit = dataBit;
        this.stopBit = stopBit;
    }

    public SerialPortListenerUtil(String portName, int baudRate) {
        this.portName = portName;
        this.baudRate = baudRate;
    }

    public void init() {
        try {
            commPortIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
        }
    }

    public void openSerialPort() {
        try {
            serialPort = (SerialPort) commPortIdentifier.open(Object.class.getSimpleName(), baudRate);
            inputStream = serialPort.getInputStream();
            //向串口添加事件监听对象。
            serialPort.addEventListener(this);
            //设置当端口有可用数据时触发事件，此设置必不可少。
            serialPort.notifyOnDataAvailable(true);
            serialPort.setSerialPortParams(9600,
                    SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        } catch (PortInUseException | IOException | TooManyListenersException | UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        String str = null;
        try {
            //如果是数据可用的时间发送，则进行数据的读写
            int availableBytes;
            byte[] bytes;
            if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                availableBytes = inputStream.available();
                //如果可用字节数大于零则开始循环并获取数据
                while (availableBytes > 0) {
                    bytes = new byte[availableBytes];
                    //从串口的输入流对象中读入数据并将数据存放到缓存数组中
                    inputStream.read(bytes);
                    //将获取到的数据进行转码并输出
                    str = new String(bytes);
                    blockingQueue.add(str);
                    availableBytes = inputStream.available();
                }
                Thread.sleep(20);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (blockingQueue.size() > 0) {
                try {
                    logger.info("接收到的数据为：" + blockingQueue.take());
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SerialPortListenerUtil serialPortListenerUtil = new SerialPortListenerUtil("COM3", 9600);
        serialPortListenerUtil.init();
        serialPortListenerUtil.openSerialPort();
        serialPortListenerUtil.start();
    }
}
