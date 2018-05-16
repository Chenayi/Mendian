package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.RefundContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/5/16.
 */
@Module
public class RefundModule {
    private RefundContract.View mView;

    public RefundModule(RefundContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    RefundContract.View provideRefundView() {
        return mView;
    }
}
