package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.GatheringOrderModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.GatheringWithOrderActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/2/11.
 */
@ActivityScope
@Component(modules = GatheringOrderModule.class, dependencies = AppComponent.class)
public interface GatheringOrderComponent {
    void inject(GatheringWithOrderActivity gatheringWithOrderActivity);
}
