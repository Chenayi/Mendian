package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.SelfDeliveryModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.SelfDeliveryActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/3/16.
 */
@ActivityScope
@Component(modules = SelfDeliveryModule.class, dependencies = AppComponent.class)
public interface SelfDeliveryComponent {
    void inject(SelfDeliveryActivity selfDeliveryActivity);
}
