package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.mvp.contract.GatheringContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/2/12.
 */
@Module
public class GatheringModule {
    private GatheringContract.View mView;

    public GatheringModule(GatheringContract.View view) {
        this.mView = view;
    }

    @FragmentScope
    @Provides
    GatheringContract.View provideGatheringView()
    {
        return mView;
    }
}
