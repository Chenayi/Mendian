package com.yaoxiaoer.mendian.base;

import android.content.Context;

import com.yaoxiaoer.mendian.http.HttpManager;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/1/25 16:32
 */

public class BasePresenter<V extends IView> implements IPresenter {
    protected Context mContext;
    protected V mView;
    protected HttpManager mHttpManager;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter(Context context, V view, HttpManager httpManager) {
        this.mContext = context;
        this.mView = view;
        this.mHttpManager = httpManager;
        this.onStart();
    }

    @Override
    public void onStart() {

    }

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void disposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onDestory() {
        disposable();
    }
}
