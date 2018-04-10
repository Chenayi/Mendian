package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.mvp.contract.SelfDeliveryContract;

import javax.inject.Inject;

/**
 * Created by Chenwy on 2018/3/16.
 */

public class SelfDeliceryPresenter extends DelayFinishPresenter<SelfDeliveryContract.View> {
    @Inject
    public SelfDeliceryPresenter(Context context, SelfDeliveryContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    @Override
    protected void updateSecond(int second) {
        mView.updateSecond(second);
    }

    @Override
    protected void close() {
        mView.close();
    }
}
