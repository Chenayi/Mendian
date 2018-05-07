package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.ParamsUtils;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.GatheringContract;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.utils.Utils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/2/12.
 */
@FragmentScope
public class GatheringPresenter extends PayResultPresenter<GatheringContract.View> {
    @Inject
    public GatheringPresenter(Context context, GatheringContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    @Override
    public void paySuccess(PayResultEntity payResultEntity) {
        mView.paySuccess(payResultEntity);
    }

    /**
     * 请求反扫收款结果
     *
     * @param authCode
     * @param orderPrice
     */
    public void requestScanPay(String authCode, String orderPrice) {
        mView.showLoadingNotBackDismiss();
        mHttpManager.obtainRetrofitService(ApiService.class)
                .requestFansaoNoOrder(ParamsUtils
                        .params("authCode", authCode)
                        .params("createIp", NetworkUtils.getIPAddress(true))
                        .params("orderPrice", orderPrice)
                        .params("userId", C.USER_ID)
                        .params("randomNum", Utils.getEncryptRadomNum())
                        .commitParams())
                .compose(RxScheduler.<BaseResponse<PayResultEntity>>compose())
                .subscribe(new BaseObserver<PayResultEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(PayResultEntity payResultEntity) {
                        mView.hideLoading();
                        mView.scanPaySuccess(payResultEntity);

                        //触发此接口让后台开始轮询支付结果
                        if (!TextUtils.isEmpty(payResultEntity.orderId)) {
                            timequeryOrderpayOrnot(payResultEntity.orderId);
                        }
                    }

                    @Override
                    protected void onHandleError(int code, String msg) {
                        //用户需要输密码
                        if (code == C.CODE_NEED_PAY_PASSWORD) {
                            ToastUtils.showShort("需要用户输入支付密码");
                            String orderId = msg;
                            //触发此接口让后台开始轮询支付结果
                            if (!TextUtils.isEmpty(orderId)) {
                                timequeryOrderpayOrnot(orderId);
                            }
                            //轮询支付结果
                            requestPayResult(2, 2, orderId);
                        } else {
                            mView.hideLoading();
                            mView.onError(code, msg);
                        }
                    }

                    @Override
                    protected void onHandleAfter() {
                    }
                });
    }
}
