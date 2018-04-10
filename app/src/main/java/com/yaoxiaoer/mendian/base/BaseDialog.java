package com.yaoxiaoer.mendian.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.ViewHolder;
import com.yaoxiaoer.mendian.App;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Chenwy on 2018/3/8.
 */

public abstract class BaseDialog<P extends IPresenter> extends BaseNiceDialog {
    private Unbinder bind;
    protected App mApp;
    protected Activity mActivity;
    protected Context mContext;
    protected Dialog mDialog;
    protected ImmersionBar mImmersionBar;
    @Inject
    protected P mPresenter;

    @Override
    public void convertView(ViewHolder viewHolder, BaseNiceDialog baseNiceDialog) {
        View view = viewHolder.getConvertView();
        ScreenAdapterTools.getInstance().loadView((ViewGroup) view);
        bind = ButterKnife.bind(this, view);
        mContext = getContext();
        mActivity = getActivity();
        mApp = (App) mActivity.getApplication();
        mDialog = getDialog();
        if (isLoadEventBus()) {
            EventBus.getDefault().register(this);
        }
        setUpComponent(mApp.getAppComponent());
        initImmersionBar();
        initData();
    }

    protected abstract void initImmersionBar();

    protected abstract void setUpComponent(AppComponent appComponent);

    protected abstract void initData();

    protected boolean isLoadEventBus() {
        return false;
    }

    @Override
    public void onDestroy() {
        if (bind != null) {
            bind.unbind();
        }
        if (isLoadEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        super.onDestroy();
    }

    protected void hideKeyboard() {
        View view = getDialog().getWindow().peekDecorView();
        KeyboardUtils.hideSoftInput(view);
    }
}
