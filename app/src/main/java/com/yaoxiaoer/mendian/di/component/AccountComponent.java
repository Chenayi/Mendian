package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.AccountModule;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.ui.fragment.AccountsFragment;

import dagger.Component;

/**
 * Created by Chenwy on 2018/2/8.
 */
@FragmentScope
@Component(modules = AccountModule.class, dependencies = AppComponent.class)
public interface AccountComponent {
    void inject(AccountsFragment accountsChildFragment);
}
