package com.fxd.java;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Map;

/**
 * Created by Fuxudong on 2017-5-19.
 * @author Fuxudong
 * @description 利用模板动态生成Word文件
 */
public class DocUtil {
    public Configuration configure=null;

    public DocUtil(){
        configure=new Configuration();
        configure.setDefaultEncoding("utf-8");
    }
    /**
     * 根据Doc模板生成word文件
     * @param dataMap 需要填入模板的数据
     * @param downloadType 文件名称
     * @param savePath 保存路径
     */
    public void createDoc(Map<Object,Object> dataMap, String downloadType, String xmlPath, String savePath,String type){
        try {
            //加载需要装填的模板
            Template template;
            //设置模板装置方法和路径，FreeMarker支持多种模板装载方法。可以重servlet，classpath,数据库装载。
            //加载模板文件，放在testDoc下
            //configure.setClassForTemplateLoading(this.getClass(), xmlPath);
            //configure.set
            configure.setDirectoryForTemplateLoading(new File(xmlPath));
            //设置对象包装器
//            configure.setObjectWrapper(new DefaultObjectWrapper());
            //设置异常处理器
            configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            //定义Template对象，注意模板类型名字与downloadType要一致
            template=configure.getTemplate(downloadType+".ftl");
            File outFile=new File(savePath);
            Writer out;
            out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            //传入数据，生成word
            template.process(dataMap, out);
            out.close();

            if ("pdf".equals(type.toLowerCase())){
                //将生成的word文件使用FileInputStream读入
                FileInputStream fileInputStream = new FileInputStream(outFile);
                PdfUtil.word2Pdf(fileInputStream,savePath);
                fileInputStream.close();
                if (outFile.exists()){
                    outFile.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *将图片加密为字符串
     * @param imgFile
     * @return
     */
    public String getImageStr(String imgFile){
        InputStream in=null;
        byte[] data=null;
        try {
            in=new FileInputStream(imgFile);
            data=new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder=new BASE64Encoder();
        return encoder.encode(data);
    }
}