package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.MainContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/3/1.
 */
@Module
public class MainModule {
    private MainContract.View mView;

    public MainModule(MainContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return mView;
    }
}
