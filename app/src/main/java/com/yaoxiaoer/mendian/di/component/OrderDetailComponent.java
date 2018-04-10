package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.OrderDetailModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.OrderDetailActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/2/7.
 */
@ActivityScope
@Component(modules = OrderDetailModule.class, dependencies = AppComponent.class)
public interface OrderDetailComponent {
    void inject(OrderDetailActivity orderDetailActivity);
}
