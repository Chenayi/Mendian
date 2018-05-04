package com.yaoxiaoer.mendian.utils;

import com.blankj.utilcode.util.EncryptUtils;
import com.yaoxiaoer.mendian.C;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Chenwy on 2018/2/5.
 * 工具类
 */

public class Utils {
    public static String getEncryptRadomNum() {
        String s = EncryptUtils.encryptMD5ToString("@#$" + C.RADOM_NUM).toLowerCase();
        return s;
    }

    public static String getPastDateStartTime(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today) + " 00:00:00";
        return result;
    }

    public static String getPastDateEndTime(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today) + " 23:59:59";
        return result;
    }

    public static String getStartDateTime(String date) {
        if (!date.contains(" ") && !date.contains(":")) {
            return date + " 00:00:00";
        }
        return date;
    }

    public static String getEndDateTime(String date) {
        if (!date.contains(" ") && !date.contains(":")) {
            return date + " 23:59:59";
        }
        return date;
    }
}
