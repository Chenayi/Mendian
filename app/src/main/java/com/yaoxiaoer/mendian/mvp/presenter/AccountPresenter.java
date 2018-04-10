package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.ParamsUtils;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.AccountContract;
import com.yaoxiaoer.mendian.mvp.entity.AccountEntity;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.CashierEntity;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.utils.Utils;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/2/8.
 */
@FragmentScope
public class AccountPresenter extends BasePresenter<AccountContract.View> {
    @Inject
    public AccountPresenter(Context context, AccountContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 获取账目列表数据
     *
     * @param pageNo
     * @param pageSize
     * @param startTime
     * @param endTime
     * @param orderUserId
     * @param payType
     * @param orderSource
     */
    public void requestAccounts(final int pageNo, int pageSize, String startTime, String endTime, String orderUserId,
                                String payType, String orderSource) {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .requestAccounts(ParamsUtils.params("userId", C.USER_ID)
                        .params("randomNum", Utils.getEncryptRadomNum())
                        .params("pageNo", pageNo)
                        .params("pageSize", pageSize)
                        .params("startTime", startTime)
                        .params("endTime", endTime)
                        .params("payType", payType)
                        .params("orderUserId", orderUserId)
                        .params("orderSource", orderSource)
                        .commitParams())
                .compose(RxScheduler.<BaseResponse<AccountEntity>>compose())
                .subscribe(new BaseObserver<AccountEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(AccountEntity accountEntity) {
                        if (pageNo == 1) {
                            mView.showAccounts(accountEntity);
                        } else {
                            mView.showMoreAccounts(accountEntity.list);
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

    /**
     * 获取收银员列表数据
     */
    public void reqestCashier() {
        mView.showLoading();
        mHttpManager.obtainRetrofitService(ApiService.class)
                .requestCashier(C.USER_ID, Utils.getEncryptRadomNum())
               .compose(RxScheduler.<BaseResponse<CashierEntity>>compose())
                .subscribe(new BaseObserver<CashierEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(CashierEntity cashierEntity) {
                        mView.showCashier(cashierEntity.nicknameList);
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
