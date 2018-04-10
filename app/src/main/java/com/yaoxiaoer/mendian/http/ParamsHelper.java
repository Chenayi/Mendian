package com.yaoxiaoer.mendian.http;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chenwy on 2017/10/27.
 */

public class ParamsHelper {
    private Map<String, Object> params;
    private Map<String, Object> headers;

    public ParamsHelper() {
        if (params == null) {
            params = new HashMap<>();
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
    }

    private static class ParamsHolder {
        private static final ParamsHelper PARAMS_HELPER = new ParamsHelper();
    }

    public static ParamsHelper getInstance() {
        return ParamsHolder.PARAMS_HELPER;
    }

    public ParamsHelper params(String key, Object value) {
        if (!TextUtils.isEmpty(key) && value != null){
            params.put(key, value);
        }
        return this;
    }

    public ParamsHelper header(String key, Object value) {
        if (!TextUtils.isEmpty(key) && value!=null){
            headers.put(key, value);
        }
        return this;
    }

    public void clearHeader() {
        headers.clear();
    }

    public void clearParams() {
        params.clear();
    }

    public Map<String, Object> commitParams() {
        return params;
    }

    public Map<String, Object> commitHeaders() {
        return headers;
    }
}
