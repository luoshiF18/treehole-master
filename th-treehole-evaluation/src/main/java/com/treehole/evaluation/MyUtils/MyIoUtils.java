package com.treehole.evaluation.MyUtils;

import java.io.*;

/**
 * io 工具类
 *
 * @auther: Yan Hao
 */
public final class MyIoUtils {

    /**
     * 强制删除某一个文件
     *
     * @param file 要删除的文件
     */
    public static void deleteFile(File file) {
        boolean result = file.delete();
        if (!result) {
            System.gc();    //回收资源
            file.delete();
        }
    }

    /**
     * 删除某个文件夹下的所有文件
     *
     * @param filePath 文件路径
     */
    public static void deleteAllFile(String filePath) {
        File file = new File(filePath);
//        如果没有该文件夹创建一个
        if (!file.exists()) {
            file.mkdirs();
        }
//        读取文件挨个删除
        File[] files = file.listFiles();
        for (File deleteFile : files) {
            deleteFile.delete();
        }
    }

    /**
     * 把文件转为字节
     *
     * @param tradeFile 要转换的文件
     * @return 文件转换后的字节
     */
    public static byte[] fileToByte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 把字节转为文件，并输出到指定文件夹中
     *
     * @param bytes    要转换的字节
     * @param filePath 要输出的文件路径
     * @param fileName 给转换后的文件起名字
     * @param fileType 转换文件的类型
     */
    public static void byteToFile(byte[] bytes, String filePath, String fileName, String fileType) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName + "." + fileType);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
