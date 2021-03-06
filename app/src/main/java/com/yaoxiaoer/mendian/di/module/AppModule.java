package com.yaoxiaoer.mendian.di.module;

import android.content.Context;


import com.baidu.tts.client.SpeechSynthesizer;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.http.HttpManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Chenwy on 2018/1/26 11:18
 */
@Module
public class AppModule {
    private Context mContext;

    public AppModule(Context context) {
        this.mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Singleton
    @Provides
    HttpManager provideHttpManager(Retrofit retrofit) {
        return new HttpManager(retrofit);
    }

    @Singleton
    @Provides
    SpeechSynthesizer provideSpeechSynthesizer(Context context) {
        SpeechSynthesizer mySynthesizer = SpeechSynthesizer.getInstance();
        mySynthesizer.setContext(context);
        mySynthesizer.setAppId(C.BAIDU_SPEECH_APP_ID);
        mySynthesizer.setApiKey(C.BAIDU_SPEECH_API_KEY, C.BAIDU_SPEECH_SECRET_KEY);
        return mySynthesizer;
    }


}
