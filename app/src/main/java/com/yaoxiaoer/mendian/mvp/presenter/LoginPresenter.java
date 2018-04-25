package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.LoginContract;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.mvp.entity.User;
import com.yaoxiaoer.mendian.mvp.entity.UserEntity;
import com.yaoxiaoer.mendian.C;

import org.litepal.crud.DataSupport;

import java.util.List;

import javax.inject.Inject;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Chenwy on 2018/1/31.
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.View> {
    @Inject
    public LoginPresenter(Context context, LoginContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 发送登录请求
     *
     * @param userName
     * @param pwd
     */
    public void login(String userName, String pwd) {
        mView.showLoading();
        mHttpManager.obtainRetrofitService(ApiService.class)
                .login(userName, pwd)
                .compose(RxScheduler.<BaseResponse<UserEntity>>compose())
                .subscribe(new BaseObserver<UserEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(UserEntity user) {
                        cacheUser(user.user);
                    }

                    @Override
                    protected void onHandleError(int code, String msg) {
                        mView.hideLoading();
                        mView.onError(code, msg);
                    }

                    @Override
                    protected void onHandleAfter() {
                    }
                });
    }

    /**
     * 缓存登录用户
     *
     * @param user
     */
    private void cacheUser(final User user) {
        addDisposable(
                Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                        user.setLocalCreateOrUpdateTime(System.currentTimeMillis());
                        boolean save = user.saveOrUpdate("u_id = ?", String.valueOf(user.getU_id()));
                        C.USER_ID = user.getU_id();
                        C.RADOM_NUM = user.getRandomNum();
                        C.STORE_ID = user.getU_sid();
                        JPushInterface.setAlias(mContext, user.getU_id(), C.STORE_ID+"");
                        SPUtils.getInstance().put(C.IS_LOGIN, true);
                        e.onNext(save);
                    }
                })
                        .compose(RxScheduler.<Boolean>compose())
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                mView.hideLoading();
                                mView.loginSuccess();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.hideLoading();
                                mView.loginSuccess();
                            }
                        })
        );
    }

    /**
     * 查找缓存中的用户名列表
     */
    public void findCacheUserNames() {
        addDisposable(
                Observable.create(new ObservableOnSubscribe<List<User>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<User>> e) throws Exception {
                        List<User> users = DataSupport.order("localCreateOrUpdateTime desc").limit(3).find(User.class);
                        e.onNext(users);
                    }
                })
                        .compose(RxScheduler.<List<User>>compose())
                        .subscribe(new Consumer<List<User>>() {
                            @Override
                            public void accept(List<User> users) throws Exception {
                                if (null != users && users.size() > 0) {
                                    mView.showCacheUsers(users);
                                }
                            }
                        })
        );

    }
}
