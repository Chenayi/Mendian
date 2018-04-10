package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.OrderModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.ui.fragment.OrderChildFragment;
import com.yaoxiaoer.mendian.ui.fragment.OrderFragment;

import dagger.Component;

/**
 * Created by Chenwy on 2018/2/1.
 */
@FragmentScope
@Component(modules = OrderModule.class, dependencies = AppComponent.class)
public interface OrderComponent {
    void inject(OrderChildFragment orderFragment);
}
