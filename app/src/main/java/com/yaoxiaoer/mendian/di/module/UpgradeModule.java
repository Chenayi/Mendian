package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.DialogScope;
import com.yaoxiaoer.mendian.mvp.contract.UpgradeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/3/8.
 */
@Module
public class UpgradeModule {
    private UpgradeContract.View mView;

    public UpgradeModule(UpgradeContract.View view) {
        this.mView = view;
    }

    @DialogScope
    @Provides
    UpgradeContract.View provideUpgradeView() {
        return mView;
    }
}
