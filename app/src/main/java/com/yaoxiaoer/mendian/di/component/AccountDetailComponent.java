package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.AccountDetailModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.AccountDetailActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/2/8.
 */
@ActivityScope
@Component(modules = AccountDetailModule.class, dependencies = AppComponent.class)
public interface AccountDetailComponent {
    void inject(AccountDetailActivity accountDetailActivity);
}
