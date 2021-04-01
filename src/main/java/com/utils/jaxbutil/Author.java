package com.utils.jaxbutil;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ROOT")
@XmlType(propOrder={"name","age","code","books"})
public class Author {
    @XmlElement(name="Name",required=true)
    private String name;
    @XmlElement(name="Age",required=true)
    private String age;
    @XmlElement(name="Code",required=true)
    private String code;
    @XmlElementWrapper(name="ROWS")
    @XmlElement(name="LIST",required=true)
    private List<Book> books;
    public String getName() {
        return name;
    }
    public String getAge() {
        return age;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String toString(){
        String result = "";
        result=this.age+","+this.name+",集合的数据：";
        for (Book book:books) {
            result+=book.getPrice()+book.getTime()+book.getBookName();
        }
        return result;
    }
}