package com.yaoxiaoer.mendian.di.module;

import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.mvp.contract.QrcodeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chenwy on 2018/2/9.
 */
@Module
public class QrcodeModule {
    private QrcodeContract.View mView;

    public QrcodeModule(QrcodeContract.View view) {
        this.mView = view;
    }

    @ActivityScope
    @Provides
    QrcodeContract.View provideQrcodeView(){
        return mView;
    }
}
