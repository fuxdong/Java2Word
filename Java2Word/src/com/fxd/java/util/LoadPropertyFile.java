package com.fxd.java.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2017-5-24.
 */
public class LoadPropertyFile {
    public Properties loadFile(String filePath) throws Exception{
        InputStream is = new BufferedInputStream(new FileInputStream(filePath));
        InputStreamReader isr = new InputStreamReader(is, "GBK");
        Properties properties = new Properties();
        try {
            properties.load(isr);
        }catch (IOException ex){
            ex.printStackTrace();
        }finally {
            is.close();
            isr.close();
        }
        return properties;
    }

    public Map<Object,Object> getProperties(Properties properties){
        Map<Object,Object> dataMap = new HashMap<Object, Object>();
        for (Object o:properties.keySet()){
            dataMap.put(o,properties.get(o));
        }
        return dataMap;
    }
}
