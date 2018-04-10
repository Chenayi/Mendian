package com.yaoxiaoer.mendian.http.interceptor;

import com.blankj.utilcode.util.NetworkUtils;
import com.yaoxiaoer.mendian.http.exception.NoNetWorkException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Chenwy on 2018/3/15.
 */

public class NetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        boolean connected = NetworkUtils.isConnected();
        if (connected) {
            return chain.proceed(chain.request());
        }
        throw new NoNetWorkException();
    }
}
