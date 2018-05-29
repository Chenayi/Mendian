package com.yaoxiaoer.mendian.mvp.presenter;
import android.content.Context;
import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.ParamsUtils;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.AccountDetailContract;
import com.yaoxiaoer.mendian.mvp.entity.AccountDetailEntity;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.RefundEntity;
import com.yaoxiaoer.mendian.utils.Utils;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/2/8.
 */
@ActivityScope
public class AccountDetailPresenter extends BasePresenter<AccountDetailContract.View> {
    @Inject
    public AccountDetailPresenter(Context context, AccountDetailContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 获取账目详情数据
     *
     * @param orderId
     */
    public void requestAccountDetail(String orderId) {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .requestAccountDetail(C.USER_ID, Utils.getEncryptRadomNum(), orderId)
                .compose(RxScheduler.<BaseResponse<AccountDetailEntity>>compose())
                .subscribe(new BaseObserver<AccountDetailEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                        mView.showLoading();
                    }

                    @Override
                    protected void onHandleSuccess(AccountDetailEntity accountDetailEntity) {
                        mView.showAccountDetail(accountDetailEntity.detail);
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
     * 提交退款
     */
    public void subRefund(String orderId, String refundPwdkey) {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .refund((ParamsUtils
                        .params("userId", C.USER_ID)
                        .params("randomNum", Utils.getEncryptRadomNum())
                        .params("orderId", orderId)
                        .params("refundPwdkey", Utils.getEncodePwdKey(refundPwdkey))
                        .commitParams()))
                .compose(RxScheduler.<BaseResponse<RefundEntity>>compose())
                .subscribe(new BaseObserver<RefundEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(RefundEntity refundEntity) {
                        mView.refundSuccess();
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
