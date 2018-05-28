package com.yaoxiaoer.mendian.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.yaoxiaoer.mendian.App;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.ui.dialog.LoadingDialog;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Chenwy on 2018/1/29.
 */

public abstract class BaseActivity<P extends IPresenter> extends SupportActivity {
    private Unbinder mBind;
    protected ImmersionBar mImmersionBar;
    protected App mApp;
    private LoadingDialog mLoading;
    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ScreenAdapterTools.getInstance().loadView((ViewGroup) getWindow().getDecorView());
        mBind = ButterKnife.bind(this);
        mApp = (App) getApplication();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initImmersinBar();
        setupFragmentComponent(mApp.getAppComponent());
        if (isLoadEventBus()) {
            EventBus.getDefault().register(this);
        }
        initDatas();
    }

    @Override
    protected void onDestroy() {
        if (mBind != null) {
            mBind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (isLoadEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * AppComponent
     *
     * @param appComponent
     */
    protected abstract void setupFragmentComponent(AppComponent appComponent);

    /**
     * 一些数据的初始化
     */
    protected abstract void initDatas();

    /**
     * 沉浸式初始化
     */
    protected void initImmersinBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.fitsSystemWindows(true, R.color.colorPrimary)
                .keyboardEnable(keyboardEnable())
                .keyboardMode(getKeyboardMode())
                .init();
    }

    /**
     * 键盘兼容沉浸式
     *
     * @return
     */
    protected boolean keyboardEnable() {
        return false;
    }


    /**
     * 键盘模式
     *
     * @return
     */
    protected int getKeyboardMode() {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
    }


    /**
     * 展示loading对话框
     */
    protected void startLoading() {
        mLoading = LoadingDialog.newInstance();
        mLoading.show(getSupportFragmentManager());
    }

    /**
     * 展示loading对话框
     *
     * @param isBackDismiss
     */
    protected void startLoading(boolean isBackDismiss) {
        mLoading = LoadingDialog.newInstance(isBackDismiss);
        mLoading.show(getSupportFragmentManager());
    }

    /**
     * 隐藏loading对话框
     */
    protected void stopLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
            mLoading = null;
        }
    }

    /**
     * 是否开启eventbus
     *
     * @return
     */
    protected boolean isLoadEventBus() {
        return false;
    }

    /**
     * activity 跳转
     *
     * @param cls
     */
    protected void jumpActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * activity 跳转
     *
     * @param cls
     */
    protected void jumpActivity(Bundle bundle, Class cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * activity 跳转
     *
     * @param cls
     */
    protected void jumpActivityForResult(int requestCode, Class cls) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * activity 跳转
     *
     * @param cls
     */
    protected void jumpActivityForResult(int requestCode, Bundle bundle, Class cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }
}
