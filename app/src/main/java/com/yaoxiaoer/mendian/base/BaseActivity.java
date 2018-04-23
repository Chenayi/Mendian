package com.yaoxiaoer.mendian.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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


    protected abstract int getLayoutId();

    protected abstract void setupFragmentComponent(AppComponent appComponent);

    protected abstract void initDatas();

    protected void initImmersinBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.fitsSystemWindows(true, R.color.colorPrimary)
                .keyboardEnable(keyboardEnable())
                .keyboardMode(getKeyboardMode())
                .init();
    }

    protected boolean keyboardEnable() {
        return false;
    }


    protected int getKeyboardMode() {
        return WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
    }


    protected void startLoading() {
        mLoading = LoadingDialog.newInstance();
        mLoading.show(getSupportFragmentManager());
    }

    protected void startLoading(boolean isBackDismiss) {
        mLoading = LoadingDialog.newInstance(isBackDismiss);
        mLoading.show(getSupportFragmentManager());
    }

    protected void stopLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
            mLoading = null;
        }
    }

    protected boolean isLoadEventBus() {
        return false;
    }

    protected void jumpActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void jumpActivity(Bundle bundle, Class cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void jumpActivityForResult(int requestCode, Class cls) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    protected void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
