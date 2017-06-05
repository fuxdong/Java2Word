package com.fxd.java;

import com.fxd.java.util.LoadPropertyFile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017-5-19.
 */



public class JFreeChartTest3
{
    static String imgSavePath = null;
    public static void main(String[] args) throws Exception
    {
        String filePath = "f:\\B.properties";
        createPie(filePath);
    }
    public static void createPie(String filePath) throws Exception{
        JFreeChart chart= ChartFactory.createPieChart("某公司人员组织数据图",getDataset(filePath),true,true,false);
        chart.setTitle(new TextTitle("某公司组织结构图",new Font("宋体", Font.BOLD+Font.ITALIC,20)));

        LegendTitle legend=chart.getLegend(0);//设置Legend
        legend.setItemFont(new Font("宋体",Font.BOLD,14));
        PiePlot plot=(PiePlot) chart.getPlot();//设置Plot
        plot.setLabelFont(new Font("隶书",Font.BOLD,16));

        //OutputStream os = new FileOutputStream("company.jpeg");//图片是文件格式的，故要用到FileOutputStream用来输出。
        OutputStream os = new FileOutputStream(imgSavePath);
        ChartUtilities.writeChartAsJPEG(os, chart, 1000, 800);
        //使用一个面向application的工具类，将chart转换成JPEG格式的图片。第3个参数是宽度，第4个参数是高度。

        os.close();//关闭输出流
    }

    private static DefaultPieDataset getDataset (String filePath) throws Exception
    {
        DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
        LoadPropertyFile load = new LoadPropertyFile();
        Properties properties = load.loadFile(filePath);
        Map<Object,Object> dataMap = load.getProperties(properties);
        for (Object o:dataMap.keySet()){
            if (!"报表图片保存路径".equals(o)){
                dpd.setValue((String)o,Double.parseDouble((String) properties.get(o)));
            }else {
                imgSavePath =(String) properties.get(o);
                System.out.println(imgSavePath);
            }

        }
//        dpd.setValue("管理人员", 25);  //输入数据
        return dpd;
    }
}
