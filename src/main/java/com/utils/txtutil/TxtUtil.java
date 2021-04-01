package com.utils.txtutil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xs
 * @data 2018/8/29 10:01
 */
public class TxtUtil {

    /**
     * 读取Txt文件得到List<String>
     *
     * @param file
     * @return
     */
    public static List<String> getStringFromTxtFile(File file) {
        List<String> list = new ArrayList<String>();
        if (file.exists()) {
            String enCoding = resolveEncoding(file);
            InputStreamReader reader = null;
            try {
                reader = new InputStreamReader(new FileInputStream(file), enCoding);
                BufferedReader br = new BufferedReader(reader);
                String info = null;
                while ((info = br.readLine()) != null) {
                    list.add(info);
                    System.out.println(info);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 通过字符串生成txt文件
     * @param list
     * @param path
     * @param encoding
     */
    public static void getTxtFileFromString(List<String> list, String path, String encoding) {
        File file = new File(path);
        try {
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file, false), encoding);
            for (int i = 0; i < list.size(); i++) {
                out.write(list.get(i) + "\r\n");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取文件的编码方式
     *
     * @param file
     * @return
     */
    public static String resolveEncoding(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[20];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 20);
            String str = first3Bytes.toString();
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0) {
                        break;
                    }
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                    {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                        {
                            continue;
                        } else {
                            break;
                        }
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

}