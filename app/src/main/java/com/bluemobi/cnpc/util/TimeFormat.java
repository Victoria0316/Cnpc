package com.bluemobi.cnpc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaoyn on 2015/7/10.
 *
 * 时间戳转为指定格式
 */
public class TimeFormat {

    public static String format(String format, String time) {
        String result = "";
        SimpleDateFormat df = new SimpleDateFormat("MMdd");
        try {
            Date date = df.parse(time);
            SimpleDateFormat df1 = new SimpleDateFormat(format);
            result = df1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
