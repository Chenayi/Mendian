package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/1/31.
 */
@Module
public class LoginModule {

    private LoginContract.View mView;

    public LoginModule(LoginContract.View mView) {
        this.mView = mView;
    }

    @ActivityScope
    @Provides
    LoginContract.View provideLoginView() {
        return mView;
    }
}
