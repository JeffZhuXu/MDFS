package com.yisinian.mdfs.system;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取系统时间的类
 * Created by zhuxu on 16/1/18.
 */
public class MDFSTime {


    public static String getPhoneTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}
