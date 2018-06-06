package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.NullEntity;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.utils.Utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/3/16.
 */

public abstract class PayResultPresenter<V extends IView> extends BasePresenter<V> {
    public PayResultPresenter(Context context, V view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 定时请求收款结果 2s
     *
     * @param initialDelay
     * @param period
     * @param orderId
     */
    public void requestPayResult(long initialDelay, long period, final String orderId) {
        Observable.interval(initialDelay, period, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(final Long aLong) {
                        mHttpManager.obtainRetrofitService(ApiService.class)
                                .queryOrderPay(C.USER_ID, Utils.getEncryptRadomNum(), orderId)
                                .compose(RxScheduler.<BaseResponse<PayResultEntity>>compose())
                                .subscribe(new BaseObserver<PayResultEntity>(mContext) {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        addDisposable(d);
                                    }

                                    @Override
                                    protected void onHandleSuccess(PayResultEntity payResultEntity) {
                                        int orderStatus = payResultEntity.orderStatus;
                                        //完成付款
                                        if (orderStatus == Order.ORDER_FINISHED) {
                                            mView.hideLoading();
                                            paySuccess(payResultEntity);
                                            disposable();
                                        }
                                    }

                                    @Override
                                    protected void onHandleError(int code, String msg) {
                                    }

                                    @Override
                                    protected void onHandleError(int code, String msg, String other) {
                                        if (!TextUtils.isEmpty(other)
                                                && (other.equals("NOTPAY") || other.equals("REVOKED"))) {
                                            mView.hideLoading();
                                            payRevoked();
                                            disposable();
                                        }
                                    }

                                    @Override
                                    protected void onHandleAfter() {
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 触发此接口让后台开始轮询支付结果
     */
    public void timequeryOrderpayOrnot(String orderId) {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .timequeryOrderpayOrnot(orderId, C.USER_ID)
                .compose(RxScheduler.<BaseResponse<NullEntity>>compose())
                .subscribe(new Observer<BaseResponse<NullEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(BaseResponse<NullEntity> nullEntityBaseResponse) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    public abstract void paySuccess(PayResultEntity payResultEntity);

    /**
     * 撤销支付
     */
    public abstract void payRevoked();
}
