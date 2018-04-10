package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.LoginModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.LoginActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/1/31.
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
