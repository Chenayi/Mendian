package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.BuildConfig;
import com.yaoxiaoer.mendian.http.interceptor.DownloadInterceptor;
import com.yaoxiaoer.mendian.http.HttpLogger;
import com.yaoxiaoer.mendian.http.interceptor.NetworkInterceptor;
import com.yaoxiaoer.mendian.http.ResponseConverterFactory;
import com.yaoxiaoer.mendian.C;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Chenwy on 2018/2/13.
 */
@Module
public class ApiModule {
    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别
            builder.addInterceptor(loggingInterceptor);
        }
        builder
                .addInterceptor(new NetworkInterceptor())
                .addInterceptor(new DownloadInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS);
        return builder.build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(C.BASE_URL)
                .client(client)
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }
}
