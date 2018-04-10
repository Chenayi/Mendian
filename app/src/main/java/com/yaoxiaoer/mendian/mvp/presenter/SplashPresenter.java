package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.mvp.contract.SplashContract;
import com.yaoxiaoer.mendian.mvp.entity.User;
import com.yaoxiaoer.mendian.C;
import org.litepal.crud.DataSupport;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by Chenwy on 2018/3/16.
 */

public class SplashPresenter extends BasePresenter<SplashContract.View> {
    @Inject
    public SplashPresenter(Context context, SplashContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    public void checkLogin() {
        Observable
                .create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                        boolean isLogin = SPUtils.getInstance().getBoolean(C.IS_LOGIN);
                        if (isLogin) {
                            User user = DataSupport
                                    .order("localCreateOrUpdateTime desc")
                                    .limit(1)
                                    .findFirst(User.class);
                            C.USER_ID = user.getU_id();
                            C.STORE_ID = user.getU_sid();
                            C.RADOM_NUM = user.getRandomNum();
                            e.onNext(true);
                        } else {
                            e.onNext(false);
                        }
                    }
                })
                .compose(RxScheduler.<Boolean>compose())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isLogin) throws Exception {
                        mView.startScanAnim(isLogin);
                    }
                });
    }
}
