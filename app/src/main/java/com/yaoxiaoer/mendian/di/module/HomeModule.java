package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.mvp.contract.HomeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/2/5.
 */
@Module
public class HomeModule {
    private HomeContract.View mView;

    public HomeModule(HomeContract.View view) {
        this.mView = view;
    }

    @FragmentScope
    @Provides
    HomeContract.View provideHomeView() {
        return mView;
    }
}
