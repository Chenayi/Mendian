package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.PaySuccessContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/3/16.
 */
@Module
public class PaySuccessModule {
    private PaySuccessContract.View mView;

    public PaySuccessModule(PaySuccessContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    PaySuccessContract.View providePaySuccessView() {
        return mView;
    }
}
