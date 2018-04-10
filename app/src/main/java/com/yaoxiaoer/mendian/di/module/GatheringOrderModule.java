package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.GatheringOrderContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/2/11.
 */
@Module
public class GatheringOrderModule {
    private GatheringOrderContract.View mView;

    public GatheringOrderModule(GatheringOrderContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    GatheringOrderContract.View provideGatheringOrderView() {
        return mView;
    }
}
