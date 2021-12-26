package com.utils.jaxbutil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder={"bookName","time","price"})
public class Book {
    @XmlElement(name="BookName")
    private String bookName;
    @XmlElement(name="Time")
    private String time;
    @XmlElement(name="Price")
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBookName() {
        return bookName;

    }
    public String getTime() {
        return time;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setTime(String time) {
        this.time = time;
    }
}