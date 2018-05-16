package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.RefundModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.InputRefundPwdActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/5/16.
 */
@ActivityScope
@Component(modules = RefundModule.class, dependencies = AppComponent.class)
public interface RefundComponent {
    void inject(InputRefundPwdActivity inputRefundPwdActivity);
}
