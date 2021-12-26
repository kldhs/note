package com.utils.serialport;

import com.utils.mqtt.MqttClientCallback;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author xs
 * @date 2020/08/27 13:28
 * 常规方式串口读写数据
 */
public class SerialPortUtil {
    private static Logger logger = LoggerFactory.getLogger(SerialPortUtil.class);
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

    public SerialPortUtil(String portName, int baudRate, int checkoutBit, int dataBit, int stopBit) {
        this.portName = portName;
        this.baudRate = baudRate;
        this.checkoutBit = checkoutBit;
        this.dataBit = dataBit;
        this.stopBit = stopBit;
    }

    public SerialPortUtil(String portName, int baudRate) {
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
        } catch (PortInUseException e) {
            e.printStackTrace();
        }
    }

    public String readData() {
        String str = null;
        try {
            inputStream = serialPort.getInputStream();
            int availableBytes;
            byte[] bytes;
            while (str == null) {
                availableBytes = inputStream.available();
                bytes = new byte[availableBytes];
                //如果可用字节数大于零则开始循环并获取数据
                while (availableBytes > 0) {
                    //从串口的输入流对象中读入数据并将数据存放到缓存数组中
                    inputStream.read(bytes);
                    //将获取到的数据进行转码并输出
                    str = new String(bytes);
                    break;
                }
                Thread.sleep(20);
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void writeData(String dataStr) {
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(dataStr.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeSerialPort() {
        if (serialPort != null) {
            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    logger.info("关闭输入流时发生IO异常");
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    logger.info("关闭输出流时发生IO异常");
                }
            }
            serialPort.close();
        }
    }


    public static void main(String[] args) {
        SerialPortUtil serialPortUtil = new SerialPortUtil("COM3", 9600);
        serialPortUtil.init();
        serialPortUtil.openSerialPort();
        serialPortUtil.writeData("1234422");
        logger.info(serialPortUtil.readData());
        serialPortUtil.closeSerialPort();
    }
}
