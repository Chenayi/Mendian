package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.HomeModule;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by Chenwy on 2018/2/5.
 */
@FragmentScope
@Component(modules = HomeModule.class,dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment homeFragment);
}
