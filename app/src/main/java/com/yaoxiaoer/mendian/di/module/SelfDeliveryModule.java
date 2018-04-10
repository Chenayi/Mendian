package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.SelfDeliveryContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/3/16.
 */
@Module
public class SelfDeliveryModule {
    private SelfDeliveryContract.View mView;

    public SelfDeliveryModule(SelfDeliveryContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    SelfDeliveryContract.View provideSelfDeliveryView() {
        return mView;
    }

}
