package com.yaoxiaoer.mendian.ui.dialog;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseDialog;
import com.yaoxiaoer.mendian.di.component.AppComponent;

import butterknife.BindView;

/**
 * Created by Chenwy on 2018/3/8.
 */

public class LoadingDialog extends BaseDialog {
    @BindView(R.id.dialog_loading_iv)
    ImageView ivLoad;
    private ObjectAnimator rotation;

    public static LoadingDialog newInstance() {
        Bundle args = new Bundle();
        LoadingDialog fragment = new LoadingDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initImmersionBar() {
    }

    @Override
    protected void setUpComponent(AppComponent appComponent) {
    }

    @Override
    protected void initData() {
        setOutCancel(false);
        startRotateAnim();
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                }
                return false;
            }
        });
    }

    private void startRotateAnim() {
        //最好是0f到359f，0f和360f的位置是重复的
        rotation = ObjectAnimator.ofFloat(ivLoad, "rotation", 0f, 359f);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setDuration(1000);
        rotation.start();
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_loading;
    }
}
