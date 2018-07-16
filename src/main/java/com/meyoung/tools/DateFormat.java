package com.meyoung.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private static SimpleDateFormat df;

    public  static String format(String type){
        //String type = "yyyy-MM-dd HH:mm:ss"；
        df = new SimpleDateFormat(type);
        String s = df.format(new Date());
        return s;
    }
}
