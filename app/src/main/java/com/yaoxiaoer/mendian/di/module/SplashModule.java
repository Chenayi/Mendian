package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.SplashContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/3/16.
 */
@Module
public class SplashModule {
    private SplashContract.View mView;

    public SplashModule(SplashContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    SplashContract.View provideSplashView() {
        return mView;
    }
}
