package com.fxd.java;


import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.*;

/**
 * Created by Administrator on 2017-5-25.
 */
public class PdfUtil {
    /**
     * 获取license
     */
    private static boolean getLicense() {
        boolean result = false;
        try {
            String str = "<License>"
                    + "<Data>"
                    + "<Products>"
                    + "<Product>Aspose.Total for Java</Product>"
                    + "<Product>Aspose.Words for Java</Product>"
                    + "</Products>"
                    + "<EditionType>Enterprise</EditionType>"
                    + "<SubscriptionExpiry>20991231</SubscriptionExpiry>"
                    + "<LicenseExpiry>20991231</LicenseExpiry>"
                    + "<SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>"
                    + "</Data>"
                    + "<Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>"
                    + "</License>";

            byte[] buf = str.getBytes();

            InputStream inputStream = new ByteArrayInputStream(buf);
            License aposeLic = new License();
            aposeLic.setLicense(inputStream);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取转换后的二进制流 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS,
     * SWF相互转换
     *
     * @param inputStream 输入流
     * @param saveFormat  保存格式
     * @return 返回二进制流
     * @throws Exception
     */
    private static byte[] getTransformBytes(InputStream inputStream,
                                            int saveFormat) throws Exception {
        // 根据输入流生成文档
        Document doc = new Document(inputStream);
        // 创建二进制输出流
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        // 保存成指定格式
        doc.save(os, saveFormat);

        // 保存生成的二进制流
        byte[] content = os.toByteArray();

        // 关闭输出流
        os.close();

        return content;
    }

    /**
     * 转换成PDF的二进制流
     *
     * @param content 原二进制流数据
     * @return
     * @throws Exception
     */
    public static byte[] transToPDF(byte[] content) throws Exception {
        // 验证License
        if (!getLicense()) {
            throw new Exception("验证码出错");
        }

        // 转化成输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(content);

        // 转化成PDF的二进制流
        return getTransformBytes(inputStream, SaveFormat.PDF);
    }

    /**
     * 将word转成pdf
     * @param inputStream
     * @param filePath 文件保存路径
     * @throws Exception
     */
    public static void word2Pdf(InputStream inputStream,String filePath) throws Exception {
        // 验证License
        if (!getLicense()) {
            return;
        }

//        File inputFile = new File("G:\\Java2Word\\ExportFile\\fxd.doc");
//        InputStream inputStream = new FileInputStream(inputFile);

        byte[] content = getTransformBytes(inputStream, SaveFormat.PDF);
        String[] path = filePath.split("\\.");
        File file = new File(path[0]+".pdf");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        try {
            //word2Pdf();

            long end = System.currentTimeMillis();
            System.out.println("转换完成..用时：" + (end - start) + "ms.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
