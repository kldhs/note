package com.utils.excelutil;

import com.utils.logutil.LogUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xs
 * @data 2018/8/24 14:08
 *Excel工具类，可用于读取Excel文件，生成Excel文件，用户界面选择文件路径
 */
public class ExcelUtil {

    /**
     * 弹出窗口，用于用户选择文件夹或文件，输出该文件、文件夹路径。
     * @return
     */
    public static String getPath() {
        JFileChooser jfc = new JFileChooser();
        //设置当前路径为桌面路径,否则将我的文档作为默认路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        jfc.setCurrentDirectory(fsv.getHomeDirectory());
        //JFileChooser.FILES_AND_DIRECTORIES 选择路径和文件
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //弹出的提示框的标题
        jfc.showDialog(new JLabel(), "确定");
        //用户选择的文件夹或文件
        File file = jfc.getSelectedFile();
        return file.toString();
    }

    /**
     * 根据输入的信息获取Excel
     *
     * @param fileLastName 文件名尾字段，文件名首字段为当前日期
     * @param sheetName    excel的sheet名
     * @param title        excel标题
     * @param header       表格的各列名称
     * @param body         表格显示的信息，为一个存放List的List
     * @param path         文件存放路径，可以写死或者写个程序从窗口获取
     */
    public static void getExcelFromObject(String fileLastName, String sheetName, String title, List<String> header, List<List> body, String path) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");               //设置文件名和路径
        String date = df.format(new Date());
        OutputStream out = null;
        try {
            out = new FileOutputStream(path + "/" + date + fileLastName + ".xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HSSFWorkbook excel = new HSSFWorkbook();                                    //新建excel报表
        HSSFSheet hssfSheet = excel.createSheet(sheetName);                         //添加一个sheet
        HSSFRow forTitle = hssfSheet.createRow(0);                                  //标题栏
        forTitle.setHeightInPoints(50);
        HSSFCellStyle styleforTitle = excel.createCellStyle();                      //创建标题的单元格样式style
        styleforTitle.setAlignment(HorizontalAlignment.CENTER);                    //水平居中
        styleforTitle.setVerticalAlignment(VerticalAlignment.CENTER);              //垂直居中
        styleforTitle.setBorderBottom(BorderStyle.THIN);                           //设置边框
        styleforTitle.setBorderLeft(BorderStyle.THIN);
        styleforTitle.setBorderRight(BorderStyle.THIN);
        styleforTitle.setBorderTop(BorderStyle.THIN);
        styleforTitle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());     //设置前背景颜色
        styleforTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFFont fontForTitle = excel.createFont();                                 //创建标题的字体样式
        fontForTitle.setBold(true);                                                 //字体加粗
        fontForTitle.setFontName("微软雅黑");                                       //设置字体类型
        fontForTitle.setFontHeightInPoints((short) 15);                             //设置字体大小
        styleforTitle.setFont(fontForTitle);                                        //为标题样式设置字体样式
        for (int i = 0; i < header.size(); i++) {                                   //创建一个cell再和并也可以，这里主要是为了设置边框实线
            HSSFCell cell1 = forTitle.createCell(i);
            cell1.setCellValue(title);                                              //设置值标题
            cell1.setCellStyle(styleforTitle);                                      //设置标题样式
        }
        hssfSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, header.size() - 1));//合并列标题
        HSSFRow forHeader = hssfSheet.createRow(1);                                 //表头
        HSSFCellStyle styleForHeader = excel.createCellStyle();                     //创建表头的单元格样式
        styleForHeader.setAlignment(HorizontalAlignment.CENTER);
        styleForHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleForHeader.setBorderBottom(BorderStyle.THIN);
        styleForHeader.setBorderLeft(BorderStyle.THIN);
        styleForHeader.setBorderRight(BorderStyle.THIN);
        styleForHeader.setBorderTop(BorderStyle.THIN);
        HSSFFont fontForHeader = excel.createFont();                                //创建字体样式
        fontForHeader.setFontName("微软雅黑");
        fontForHeader.setFontHeightInPoints((short) 12);
        styleForHeader.setFont(fontForHeader);
        for (int columnNum = 0; columnNum < header.size(); columnNum++) {           //创建单元格，并设置单元格的值
            HSSFCell hssfCell = forHeader.createCell(columnNum);
            hssfCell.setCellValue(header.size() < columnNum ? "-" : header.get(columnNum));
            hssfCell.setCellStyle(styleForHeader);
        }
        for (int rowNum = 0; rowNum < body.size(); rowNum++) {                      //设置主体数据
            HSSFRow hssfRow = hssfSheet.createRow(rowNum + 2);                      //往excel表格创建一行，excel的行号是从0开始的
            List data = body.get(rowNum);
            hssfSheet.autoSizeColumn(rowNum + 2, true);                             //设置每行各列的宽度自适应
            HSSFCellStyle styleForBody = excel.createCellStyle();                   // 第三步创建标题的单元格样式style以及字体样式headerFont1
            styleForBody.setAlignment(HorizontalAlignment.CENTER);                 //水平居中
            styleForBody.setVerticalAlignment(VerticalAlignment.CENTER);           //垂直居中
            HSSFFont fontForBody = excel.createFont();                              //创建字体样式
            fontForBody.setFontName("微软雅黑");                                    //设置字体类型
            fontForBody.setFontHeightInPoints((short) 10);                          //设置字体大小
            styleForBody.setFont(fontForBody);                                      //为标题样式设置字体样式
            styleForBody.setBorderBottom(BorderStyle.THIN);
            styleForBody.setBorderLeft(BorderStyle.THIN);
            styleForBody.setBorderRight(BorderStyle.THIN);
            styleForBody.setBorderTop(BorderStyle.THIN);
            for (int columnNum = 0; columnNum < data.size(); columnNum++) {         //主要是为了区别开数据中的字符串与数字类型
                HSSFCell hssfCell = hssfRow.createCell(columnNum);                  //创建单元格
                //设置各列宽度由单元格里面的内容而定，主要针对非中文情景
                //hssfSheet.setColumnWidth(columnNum,data.get(columnNum).toString().getBytes().length*256);
                if (data.get(columnNum) instanceof String) {
                    hssfCell.setCellValue((String) data.get(columnNum));
                } else {
                    hssfCell.setCellValue(Double.valueOf(String.valueOf(data.get(columnNum))));     //设置单元格的值
                }
                hssfCell.setCellStyle(styleForBody);
            }
        }
        try {
            excel.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取指定的Excel文件返回Java对象
     *
     * @param
     * @return
     */
    public static List<List> getObjectFromExcel(String excelFileName) {
        List<List> sheetData = new ArrayList<List>();
        try {
            Workbook wb;
            InputStream fis = ExcelUtil.class.getClassLoader().getResourceAsStream(excelFileName);
            if (fis != null) {
                wb = new HSSFWorkbook(fis);
                Sheet sheet = wb.getSheetAt(0);
                for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    List rowData = new ArrayList();
                    Row row = sheet.getRow(rowNum);
                    for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        if (cell != null) {
                            rowData.add(cell.toString());
                        }
                    }
                    sheetData.add(rowData);
                }
            } else {
                System.out.println("找不到文件名为："+excelFileName+"的文件。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheetData;
    }

}
