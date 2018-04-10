package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.AccountDetailContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/2/8.
 */
@Module
public class AccountDetailModule {
    private AccountDetailContract.View mView;

    public AccountDetailModule(AccountDetailContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    AccountDetailContract.View provideAccountDetailView() {
        return mView;
    }
}
