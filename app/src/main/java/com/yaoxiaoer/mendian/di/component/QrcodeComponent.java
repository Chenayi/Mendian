package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.QrcodeModule;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.ui.activity.QrcodeActivity;

import dagger.Component;

/**
 * Created by Chenwy on 2018/2/9.
 */
@ActivityScope
@Component(modules = QrcodeModule.class, dependencies = AppComponent.class)
public interface QrcodeComponent {
    void inject(QrcodeActivity qrcodeActivity);
}
