package com.yaoxiaoer.mendian.di.component;

import android.content.Context;

import com.yaoxiaoer.mendian.di.module.ApiModule;
import com.yaoxiaoer.mendian.di.module.AppModule;
import com.yaoxiaoer.mendian.http.HttpManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Chenwy on 2018/1/26 11:15
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {
    HttpManager httpManager();
    Context getContext();
}
