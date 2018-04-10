package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.SplashModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.SplashActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/3/16.
 */
@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
