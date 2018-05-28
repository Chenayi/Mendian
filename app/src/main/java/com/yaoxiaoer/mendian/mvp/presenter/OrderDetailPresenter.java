package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.event.OrderStatusChangeEvent;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.OrderDetailContract;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.OrderDetailEntity;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/2/7.
 * 订单详情
 */
@ActivityScope
public class OrderDetailPresenter extends BasePresenter<OrderDetailContract.View> {
    @Inject
    public OrderDetailPresenter(Context context, OrderDetailContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 获取订单详情数据
     *
     * @param orderId
     */
    public void requestOrderDetail(final int orderId) {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .requestOrderDetail(C.USER_ID, Utils.getEncryptRadomNum(), orderId)
                .compose(RxScheduler.<BaseResponse<OrderDetailEntity>>compose())
                .subscribe(new BaseObserver<OrderDetailEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(OrderDetailEntity orderDetailEntity) {
                        mView.showOrderDetails(orderDetailEntity);
                    }

                    @Override
                    protected void onHandleError(int code, String msg) {
                        mView.onError(code, msg);
                    }

                    @Override
                    protected void onHandleAfter() {
                        mView.hideLoading();
                    }
                });
    }

    /**
     * 如果是“未处理”情况下，需要请求一下此接口，因为状态有可能已变为“已完成”
     *
     * @param orderId
     */
    public void requestPayResult(final int orderId) {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .queryOrderPay(C.USER_ID, Utils.getEncryptRadomNum(), String.valueOf(orderId))
                .compose(RxScheduler.<BaseResponse<PayResultEntity>>compose())
                .subscribe(new BaseObserver<PayResultEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(PayResultEntity payResultEntity) {
                        int orderStatus = payResultEntity.orderStatus;
                        //已完成
                        if (orderStatus == Order.ORDER_FINISHED) {
                            EventBus.getDefault().post(new OrderStatusChangeEvent());
                        }
                    }

                    @Override
                    protected void onHandleError(int code, String msg) {
                    }

                    @Override
                    protected void onHandleAfter() {
                        requestOrderDetail(orderId);
                    }
                });
    }

    /**
     * 确认取货
     */
    public void selfDelivery(String orderId) {
        mView.showLoading();
        mHttpManager.obtainRetrofitService(ApiService.class)
                .selfDelivery(C.USER_ID, Utils.getEncryptRadomNum(), orderId)
                .compose(RxScheduler.<BaseResponse<PayResultEntity>>compose())
                .subscribe(new BaseObserver<PayResultEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(PayResultEntity payResultEntity) {
                        mView.selfDeliverySuccess(payResultEntity);
                        EventBus.getDefault().post(new OrderStatusChangeEvent());
                    }

                    @Override
                    protected void onHandleError(int code, String msg) {
                        mView.onSelfDeliveryError(code, msg);
                    }

                    @Override
                    protected void onHandleAfter() {
                        mView.hideLoading();
                    }
                });
    }
}
