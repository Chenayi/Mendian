package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.FragmentScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.HomeContract;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.HomeEntity;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.utils.Utils;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/2/5.
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.View> {
    @Inject
    public HomePresenter(Context context, HomeContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 获取首页数据
     *
     * @param isShowLoading
     */
    public void requestHomeDatas(final boolean isShowLoading) {
        if (isShowLoading) {
            mView.showLoading();
        }
        mHttpManager.obtainRetrofitService(ApiService.class)
                .requestHomes(C.USER_ID, Utils.getEncryptRadomNum())
               .compose(RxScheduler.<BaseResponse<HomeEntity>>compose())
                .subscribe(new BaseObserver<HomeEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(HomeEntity homeEntity) {
                        mView.showHomeDatas(homeEntity);
                    }

                    @Override
                    protected void onHandleError(int code, String msg) {
                        mView.onError(code, msg);
                    }

                    @Override
                    protected void onHandleAfter() {
                        if (isShowLoading){
                            mView.hideLoading();
                        }
                    }
                });
    }
}
