package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.ParamsUtils;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.OrderContract;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.OrderEntity;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.utils.Utils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/2/1.
 */
@FragmentScope
public class OrderPresenter extends BasePresenter<OrderContract.View> {
    @Inject
    public OrderPresenter(Context context, OrderContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 获取订单列表数据
     *
     * @param orderStatus
     * @param pageNo
     * @param pageSize
     * @param orderSource
     * @param orderCode
     * @param customerName
     * @param startTime
     * @param endTime
     * @param storeId
     */
    public void requestOrders(String orderStatus, final int pageNo, int pageSize, String orderSource, final String orderCode
            , String customerName, String startTime, String endTime, String storeId) {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .requestOrders(ParamsUtils.params("pageNo", pageNo)
                        .params("pageSize", pageSize)
                        .params("userId", C.USER_ID)
                        .params("randomNum", Utils.getEncryptRadomNum())
                        .params("orderSource", orderSource)
                        .params("orderCode", orderCode)
                        .params("customerName", customerName)
                        .params("startTime", startTime)
                        .params("endTime", endTime)
                        .params("storeId", storeId)
                        .params("orderStatus", orderStatus)
                        .commitParams())
                .compose(RxScheduler.<BaseResponse<OrderEntity>>compose())
                .subscribe(new BaseObserver<OrderEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(OrderEntity orderEntity) {
                        if (pageNo <= 1) {
                            mView.showOrders(orderEntity.list);
                        } else {
                            mView.showMoreOrders(orderEntity.list);
                        }
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
}
