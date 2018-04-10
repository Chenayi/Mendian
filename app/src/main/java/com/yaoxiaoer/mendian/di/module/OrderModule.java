package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.mvp.contract.OrderContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/2/1.
 */
@Module
public class OrderModule {
    private OrderContract.View mView;

    public OrderModule(OrderContract.View view) {
        this.mView = view;
    }

    @FragmentScope
    @Provides
    OrderContract.View provideOrderView() {
        return mView;
    }
}
