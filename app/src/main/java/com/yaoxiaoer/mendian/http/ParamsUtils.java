package com.yaoxiaoer.mendian.http;

import android.text.TextUtils;

/**
 * Created by Chenwy on 2017/10/27.
 */

public class ParamsUtils {
    public static ParamsHelper params(String key, Object value) {
        ParamsHelper paramsHelper = ParamsHelper.getInstance();
        paramsHelper.clearParams();
        if (!TextUtils.isEmpty(key) && value != null){
            paramsHelper.params(key, value);
        }
        return paramsHelper;
    }

    public static ParamsHelper header(String key, Object value) {
        ParamsHelper paramsHelper = ParamsHelper.getInstance();
        paramsHelper.clearHeader();
        if (!TextUtils.isEmpty(key) && value != null){
            paramsHelper.header(key, value);
        }
        return paramsHelper;
    }
}
