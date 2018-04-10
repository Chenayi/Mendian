package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.RxScheduler;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by Chenwy on 2018/3/16.
 */

public abstract class DelayFinishPresenter<V extends IView> extends BasePresenter<V> {
    public DelayFinishPresenter(Context context, V view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 倒计时关闭
     *
     * @param time
     */
    public void delayFinish(int time) {
        final int countTime = time;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long increaseTime) throws Exception {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 2)
               .compose(RxScheduler.<Integer>compose())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer < 0) {
                            integer = 0;
                        }
                        updateSecond(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        disposable();
                        close();
                    }
                });
    }

    protected abstract void updateSecond(int second);

    protected abstract void close();
}
