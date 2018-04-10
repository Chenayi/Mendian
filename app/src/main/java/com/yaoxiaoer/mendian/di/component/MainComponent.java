package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.MainModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.MainActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/3/1.
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
