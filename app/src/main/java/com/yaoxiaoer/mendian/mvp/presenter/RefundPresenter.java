package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.mvp.contract.RefundContract;

import javax.inject.Inject;

/**
 * Created by Chenwy on 2018/5/16.
 */
@ActivityScope
public class RefundPresenter extends DelayFinishPresenter<RefundContract.View> {
    @Inject
    public RefundPresenter(Context context, RefundContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    @Override
    protected void updateSecond(int second) {
    }

    @Override
    protected void close() {
        mView.close();
    }

    /**
     * 提交退款
     */
    public void subRefund() {
        mView.refundSuccess();
    }
}
