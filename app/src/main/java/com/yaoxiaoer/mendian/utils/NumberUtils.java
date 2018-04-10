package com.yaoxiaoer.mendian.utils;

/**
 * Created by Chenwy on 2018/3/5.
 */

public class NumberUtils {
    public static int parseInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 0;
        }
    }

    public static float parseFloat(String num) {
        try {
            return Float.parseFloat(num);
        } catch (Exception e) {
            return 0.0f;
        }
    }
}
