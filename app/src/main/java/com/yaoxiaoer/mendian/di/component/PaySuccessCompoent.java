package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.PaySuccessModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.PaySuccessActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/3/16.
 */
@ActivityScope
@Component(modules = PaySuccessModule.class, dependencies = AppComponent.class)
public interface PaySuccessCompoent {
    void inject(PaySuccessActivity paySuccessActivity);
}
