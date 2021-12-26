package com.utils.jaxbutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;

/**
 * @author xs
 * @date 2019/08/26 14:42
 * xml文件工具类，xml格式字符串转对象，xml文件转对象，对象转xml文件，对象转xml字符串
 */
public class JaxbUtil {
    private static Logger logger = LoggerFactory.getLogger(JaxbUtil.class);
    /**
     * 将Java对象转换为Xml格式的字符串输出
     * @param obj
     * @return
     */
    public static String getXmlStringFromJavaBean(Object obj) {
        try {
            //使用作为参数传递的类以及可从这些类静态获得的类来实现初始化的
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            //创造一个Marshaller对象，用来将Object转换为XML
            Marshaller marshaller = context.createMarshaller();
            //设置编码方式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            //设置是否为文档事件
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(baos, (String) marshaller.getProperty(Marshaller.JAXB_ENCODING));
            xmlStreamWriter.writeStartDocument((String) marshaller.getProperty(Marshaller.JAXB_ENCODING), "1.0");
            marshaller.marshal(obj, xmlStreamWriter);
            xmlStreamWriter.writeEndDocument();
            xmlStreamWriter.close();
            //输出XML格式的字符串
            return new String(baos.toString("GBK"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *将Xml格式的字符串转换为指定的Java对象
     * @param xml
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T getJavaBeanFromXmlString(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            //生成Unmarshaller对象，用于将XML格式的字符串转换为JavaBean
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将Java对象转换为Xml文件输出
     * @param object
     * @param filePath
     */
    public static void getXmlFileFromJavaBean(Object object, String filePath) {
        try {
            if (object == null || filePath == null || "".equals(filePath)) {
                return;
            }
            File file = new File(filePath);
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller jaxbMarshaller = context.createMarshaller();
            //是否格式化输出xml文件
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //设置编码
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
            //写入到文件中
            jaxbMarshaller.marshal(object, file);
            logger.info("xml已写入到文件" + filePath);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将xml文件转换为指定Java对象
     * @param filePath
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T getJavaBeanFromXmlFile(String filePath, Class<T> c) {
        try {
            File xmlFile = new File(filePath);
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T t = (T) unmarshaller.unmarshal(xmlFile);
            return t;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

}
