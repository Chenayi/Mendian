package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.OrderDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/2/7.
 */

@Module
public class OrderDetailModule {
    private OrderDetailContract.View mView;

    public OrderDetailModule(OrderDetailContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    OrderDetailContract.View provideOrderDetailView() {
        return mView;
    }
}
