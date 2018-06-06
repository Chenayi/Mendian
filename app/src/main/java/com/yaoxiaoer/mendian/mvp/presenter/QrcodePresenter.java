package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.ParamsUtils;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.QrcodeContract;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.mvp.entity.QrcodeEntity;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.utils.Utils;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/2/9.
 */
@ActivityScope
public class QrcodePresenter extends PayResultPresenter<QrcodeContract.View> {
    @Inject
    public QrcodePresenter(Context context, QrcodeContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    @Override
    public void paySuccess(PayResultEntity payResultEntity) {
        mView.paySuccess(payResultEntity);
    }

    @Override
    public void payRevoked() {
        ToastUtils.showShort("用户已取消支付");
    }


    /**
     * 获取收款二维码
     *
     * @param isFromOrder
     * @param payType
     * @param orderPrice
     * @param orderId
     */
    public void requestQrcode(final boolean isFromOrder, String payType, String orderPrice, final String orderId) {
        mView.showLoading();
        Map<String, Object> params = ParamsUtils
                .params("payType", payType)
                .params("createIp", NetworkUtils.getIPAddress(true))
                .params("orderId", orderId)
                .params("orderPrice", orderPrice)
                .params("userId", C.USER_ID)
                .params("randomNum", Utils.getEncryptRadomNum())
                .commitParams();
        ApiService apiService = mHttpManager.obtainRetrofitService(ApiService.class);
        Observable<BaseResponse<QrcodeEntity>> observable;
        if (isFromOrder) {
            observable = apiService.requestQrcode(params);
        } else {
            observable = apiService.requestQrcodeNoOrder(params);
        }
        observable.compose(RxScheduler.<BaseResponse<QrcodeEntity>>compose())
                .subscribe(new BaseObserver<QrcodeEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(QrcodeEntity qrcodeEntity) {
                        mView.showQrCode(qrcodeEntity);

                        String oId;
                        if (isFromOrder) {
                            oId = orderId;
                        } else {
                            oId = qrcodeEntity.orderId;
                        }
                        requestPayResult(2, 2, oId);
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
