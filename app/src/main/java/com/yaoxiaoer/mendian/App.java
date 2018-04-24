package com.yaoxiaoer.mendian;

import android.app.Application;
import android.content.res.Configuration;

import com.baidu.tts.client.SpeechSynthesizer;
import com.blankj.utilcode.util.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerAppComponent;
import com.yaoxiaoer.mendian.di.module.AppModule;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.litepal.LitePal;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Chenwy on 2018/1/29.
 */

public class App extends Application {
    private AppComponent mAppComponent;
    private SpeechSynthesizer mySynthesizer;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //UtilsCode
        Utils.init(this);
        //Logger
        Logger.addLogAdapter(new AndroidLogAdapter());
        //屏幕适配
        ScreenAdapterTools.init(this);
        //二维码
        ZXingLibrary.initDisplayOpinion(this);
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //数据库
        LitePal.initialize(this);
        //语音初始化
        initVoice();
    }

    private void initVoice() {
        mySynthesizer = SpeechSynthesizer.getInstance();
        mySynthesizer.setContext(this);
        mySynthesizer.setAppId("11149254");
        mySynthesizer.setApiKey("R9SkKAjFlBAo9GGi7P962YiW", "d770298903dbddab367e4e71346feced");
    }

    public SpeechSynthesizer getSpeechSynthesizer() {
        return mySynthesizer;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ScreenAdapterTools.init(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
