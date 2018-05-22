package com.yaoxiaoer.mendian.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.TextView;

import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseDialog;
import com.yaoxiaoer.mendian.di.component.AppComponent;

import butterknife.BindView;

public class SimpleDialog extends BaseDialog {
    @BindView(R.id.tv_msg)
    TextView tvMsg;

    private String msg;
    private OnBackListener onBackListener;

    public static SimpleDialog newInstance(String msg) {

        Bundle args = new Bundle();
        args.putString("msg", msg);
        SimpleDialog fragment = new SimpleDialog();
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
        msg = getArguments().getString("msg");
        if (!TextUtils.isEmpty(msg)) {
            tvMsg.setText(msg);
        }

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (onBackListener != null) {
                        onBackListener.onBack();
                    }
                }
                return false;
            }
        });
    }

    public SimpleDialog setOnBackListener(OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
        return this;
    }

    public interface OnBackListener {
        void onBack();
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_simple;
    }
}
