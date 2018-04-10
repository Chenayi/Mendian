package com.yaoxiaoer.mendian.http;
import retrofit2.Retrofit;

/**
 * Created by Chenwy on 2018/2/13.
 */

public class HttpManager {
    private Retrofit mRetrofit;

    public HttpManager(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public <T> T obtainRetrofitService(Class<T> service) {
        return mRetrofit.create(service);
    }
}
