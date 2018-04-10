package com.yaoxiaoer.mendian.di.module;

import android.content.Context;


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

    @Singleton
    @Provides
    HttpManager provideHttpManager(Retrofit retrofit) {
        return new HttpManager(retrofit);
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
