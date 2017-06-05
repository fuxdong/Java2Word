package com.fxd.java;

import com.fxd.java.util.LoadPropertyFile;
import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017-5-23.
 */
public class TestDoc {

    public static void main(String[] args) throws Exception{
        //要加载模板名称
        String xmlTemplate = null;
        //要加载的模板路径
        String xmlPath = null;
        //模板配置文件
        String propertyFilePath = null;
        //报表数据配置文件
        String dataFilePath = null;
        //生成文档保存路径
        String fileSavePath = null;
        String fileExportType = null;
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        options.addOption("t","template",true,"Enter template name：");
        options.addOption("r","resource",true,"Enter property file name and path：");
        options.addOption("p","report",true,"Enter data property file name and path：");
        options.addOption("e","export",true,"choose file export type：");

        //获取命令以及命令所对应的传入参数
        try {
            CommandLine cl = parser.parse(options,args);
            if(cl.hasOption('t')){
                xmlTemplate = cl.getOptionValue('t');
            }

            if(cl.hasOption('r')){
                propertyFilePath = cl.getOptionValue('r');
                System.out.println(propertyFilePath);
            }

            if(cl.hasOption('p')){
                dataFilePath = cl.getOptionValue('p');
            }

            if(cl.hasOption('e')){
                fileExportType = cl.getOptionValue('e');
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JFreeChartTest3 jFreeChartTest3 = new JFreeChartTest3();
        //生成饼状图
        jFreeChartTest3.createPie(dataFilePath);
        //判断是否传入所需的参数
        if (xmlTemplate!=null&&propertyFilePath!=null&&dataFilePath!=null){
            LoadPropertyFile load = new LoadPropertyFile();
            Properties properties = load.loadFile(propertyFilePath);
            DocUtil docUtil=new DocUtil();
            Map<Object, Object> dataMap=new HashMap<Object, Object>();

            xmlPath = properties.getProperty(xmlTemplate);
//            System.out.println(xmlPath);

            fileSavePath = properties.getProperty("文件保存路径");
            for (Object o:properties.keySet()){
                if (!xmlTemplate.equals(o)||!"文件保存路径".equals(o)){
                    if ("pic".equals(o)){
                        dataMap.put(o,docUtil.getImageStr((String) properties.get(o)));
                    }else{
                        dataMap.put(o,properties.getProperty((String) o));
                    }
                }
            }

            docUtil.createDoc(dataMap, xmlTemplate,xmlPath, fileSavePath,fileExportType);

        }else {
            throw new IllegalArgumentException("所需传入的参数有误,xmlTemplate：" + xmlTemplate +
                    " , propertyFilePath：" + propertyFilePath + " , dataFilePath：" + dataFilePath + ",fileExportType：" + fileExportType);
        }

    }
}
