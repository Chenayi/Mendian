package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.ParamsUtils;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.RefundContract;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.RefundEntity;
import com.yaoxiaoer.mendian.utils.Utils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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
    public void subRefund(String orderId, String refundPwdkey) {
//        mHttpManager.obtainRetrofitService(ApiService.class)
//                .refund((ParamsUtils
//                        .params("userId", C.USER_ID)
//                        .params("randomNum", Utils.getEncryptRadomNum())
//                        .params("orderId", orderId)
//                        .params("refundPwdkey", getEncodePwdKey(refundPwdkey))
//                        .commitParams()))
//                .compose(RxScheduler.<BaseResponse<RefundEntity>>compose())
//                .subscribe(new BaseObserver<RefundEntity>(mContext) {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        addDisposable(d);
//                    }
//
//                    @Override
//                    protected void onHandleSuccess(RefundEntity refundEntity) {
//                    }
//
//                    @Override
//                    protected void onHandleError(int code, String msg) {
//                    }
//
//                    @Override
//                    protected void onHandleAfter() {
//                    }
//                });

        mView.refundSuccess();
    }


    public String getEncodePwdKey(String refundPwdkey) {
        return EncryptUtils.encryptMD5ToString("@#$@" + refundPwdkey);
    }
}
