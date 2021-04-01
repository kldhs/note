package com.utils;

import com.utils.excelutil.ExcelUtil;
import com.utils.jaxbutil.Author;
import com.utils.jaxbutil.Book;
import com.utils.jaxbutil.JaxbUtil;
import com.utils.logutil.LogUtil;
import com.utils.txtutil.TxtUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xs
 * @date 2020/08/26 14:42
 */
public class MainClass {

    public static void main(String[] args) {
        //ExcelUtil.getPath();
        //ExcelUtil.getObjectFromExcel("testwarehouse.xlsx");
        //JaxbUtil.getXmlFileFromJavaBean((Object)Author,"E:\\aaa.xml");
        //logTest();
        //txtTest();
    }

    public static void txtTest() {
        File file = new File("E:\\log4j.txt");
        List<String> list = TxtUtil.getStringFromTxtFile(file);
        System.out.println(list.toArray());
    }

    public static void logTest() {
        LogUtil.threadInfo( "threadInfo");
        LogUtil.threadError( "threadError");
        LogUtil.threadDebug( "threadDebug");
        LogUtil.threadWarn( "threadWarn");
        LogUtil.timerInfo( "timerInfo");
        LogUtil.timerError( "timerError");
        LogUtil.timerDebug( "timerDebug");
        LogUtil.timerWarn( "timerWarn");
        LogUtil.socketInfo( "socketInfo");
        LogUtil.socketError( "socketError");
        LogUtil.socketDebug( "socketDebug");
        LogUtil.socketWarn( "socketWarn");

    }

    public static void excelTest() {
        //String path = ExcelUtil.getPath();
        List<String> header = new ArrayList();
        List header1 = new ArrayList();
        List body = new ArrayList();

        header.add("一");
        header.add("二");
        header.add("三");
        header.add("四");
        header.add("五");
        header.add("六");
        header.add("七");
        header.add("八");
        header.add("九");

        header1.add(1);
        header1.add(2);
        header1.add(3);
        header1.add(4);
        header1.add(5);
        header1.add("六");
        header1.add("七");
        header1.add("八");
        header1.add("九");
        body.add(header1);
        //ExcelUtil.getExcelFromObject("test", "excel", "测试", header, body, path);
        ExcelUtil.getObjectFromExcel("C:\\20180824025506test.xls");
    }

    public void jaxbTest() {
        Author author = new Author();
        author.setAge("28");
        author.setCode("3818271");
        author.setName("xiaozhang");
        Book firBook = new Book();
        Book secBook = new Book();
        firBook.setBookName("***完了么");
        secBook.setBookName("***起义了么");
        firBook.setTime("20180824");
        secBook.setTime("20180812");
        firBook.setPrice("$38");
        secBook.setPrice("$48");
        List<Book> books = new ArrayList<Book>();
        books.add(firBook);
        books.add(secBook);
        author.setBooks(books);
        System.out.println(JaxbUtil.getXmlStringFromJavaBean(author));
    }
}
