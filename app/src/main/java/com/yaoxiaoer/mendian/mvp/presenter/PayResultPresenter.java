package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.C;
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
                    public void onNext(Long aLong) {
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
                                        if (orderStatus == 3) {
                                            mView.hideLoading();
                                            paySuccess(payResultEntity);
                                            disposable();
                                        }
                                    }

                                    @Override
                                    protected void onHandleError(int code, String msg) {
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

    public abstract void paySuccess(PayResultEntity payResultEntity);
}
