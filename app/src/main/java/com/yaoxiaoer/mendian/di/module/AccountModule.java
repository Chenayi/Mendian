package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.mvp.contract.AccountContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/2/8.
 */
@Module
public class AccountModule {
    private AccountContract.View mView;

    public AccountModule(AccountContract.View view) {
        this.mView = view;
    }

    @FragmentScope
    @Provides
    AccountContract.View provideAccountView() {
        return mView;
    }
}
