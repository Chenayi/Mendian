package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.GatheringModule;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.ui.fragment.GatheringFragment;

import dagger.Component;

/**
 * Created by Chenwy on 2018/2/11.
 */
@FragmentScope
@Component(modules = GatheringModule.class, dependencies = AppComponent.class)
public interface GatheringComponent {
    void inject(GatheringFragment gatheringFragment);
}
