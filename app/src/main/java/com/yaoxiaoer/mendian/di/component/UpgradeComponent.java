package com.yaoxiaoer.mendian.di.component;

import com.yaoxiaoer.mendian.di.module.UpgradeModule;
import com.yaoxiaoer.mendian.di.scope.DialogScope;
import com.yaoxiaoer.mendian.ui.dialog.UpgradeDialog;

import dagger.Component;

/**
 * Created by Chenwy on 2018/3/8.
 */
@DialogScope
@Component(modules = UpgradeModule.class, dependencies = AppComponent.class)
public interface UpgradeComponent {
    void inject(UpgradeDialog upgradeDialog);
}
