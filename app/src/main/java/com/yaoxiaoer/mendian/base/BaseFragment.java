package com.yaoxiaoer.mendian.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import com.yaoxiaoer.mendian.App;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.ui.dialog.LoadingDialog;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;
import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Chenwy on 2018/1/25 14:24
 */

public abstract class BaseFragment<P extends IPresenter> extends SupportFragment {
    private Unbinder mBind;
    @Inject
    protected P mPresenter;
    private LoadingDialog mLoading;
    protected App mApp;
    /**
     * 是否已获取过数据
     */
    protected boolean isAcquired = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ScreenAdapterTools.getInstance().loadView((ViewGroup) view);
        mBind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        if (mBind != null) {
            mBind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        if (isLoadEvenetBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mApp = (App) getActivity().getApplication();
        setupFragmentComponent(mApp.getAppComponent());
        if (isLoadEvenetBus()) {
            EventBus.getDefault().register(this);
        }
        initData();
        isAcquired = true;
    }


    protected abstract void setupFragmentComponent(AppComponent appComponent);

    protected abstract void initData();

    protected boolean isLoadEvenetBus() {
        return false;
    }

    protected void jumpActivityForResult(int requestCode, Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    protected void jumpActivity(Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    protected void jumpActivity(Bundle bundle, Class cls) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startLoading() {
        mLoading = LoadingDialog.newInstance();
        mLoading.show(getChildFragmentManager());
    }

    protected void stopLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
            mLoading = null;
        }
    }

    protected void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = getActivity().getCurrentFocus();
        if (view == null) {
            view = new View(getContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
