package com.yaoxiaoer.mendian.http;


import android.content.Context;

import com.orhanobut.logger.Logger;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.http.exception.NeedPayPwdException;
import com.yaoxiaoer.mendian.http.exception.NoNetWorkException;
import com.yaoxiaoer.mendian.http.exception.ResultException;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import com.yaoxiaoer.mendian.C;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2017/10/16.
 */

public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private Context mContext;

    public BaseObserver(Context context) {
        mContext = context;
    }

    @Override
    public abstract void onSubscribe(Disposable d);

    @Override
    public void onNext(BaseResponse<T> t) {
        onHandleSuccess(t.data);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ResultException) {
            ResultException resultException = (ResultException) e;
            onHandleError(resultException.errorCode, resultException.errorMsg);
        } else if (e instanceof SocketTimeoutException) {
            onHandleError(C.CODE_HTTP_EXCEPTION, mContext.getResources().getString(R.string.connect_time_out));
        } else if (e instanceof NoNetWorkException || e instanceof IOException) {
            onHandleError(C.CODE_NO_NETWORK_EXCEPTION, mContext.getResources().getString(R.string.no_net_work));
        } else if (e instanceof IOException) {
            onHandleError(C.CODE_CONNECT_EXCEPTION, mContext.getResources().getString(R.string.connect_fail));
        }
        //反扫需要支付密码
        else if (e instanceof NeedPayPwdException) {
            NeedPayPwdException needPayPwdException = (NeedPayPwdException) e;
            onHandleError(C.CODE_NEED_PAY_PASSWORD, needPayPwdException.orderId);
        } else {
            onHandleError(C.CODE_OTHER_EXCEPTION, e.getMessage());
        }
        onHandleAfter();
    }

    @Override
    public void onComplete() {
        onHandleAfter();
    }

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onHandleSuccess(T t);

    /**
     * 失败回调
     *
     * @param code
     * @param msg
     */
    protected abstract void onHandleError(int code, String msg);

    /**
     * 成功或失败处理完后的回调
     */
    protected abstract void onHandleAfter();
}
