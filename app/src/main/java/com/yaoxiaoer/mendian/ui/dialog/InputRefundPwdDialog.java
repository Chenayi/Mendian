package com.yaoxiaoer.mendian.ui.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseDialog;
import com.yaoxiaoer.mendian.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

public class InputRefundPwdDialog extends BaseDialog {
    @BindView(R.id.et_pwd)
    EditText etPwd;

    private onPasswordConfigListener onPasswordConfigListener;

    public static InputRefundPwdDialog newInstance() {
        Bundle args = new Bundle();
        InputRefundPwdDialog fragment = new InputRefundPwdDialog();
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

    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_input_refund_pwd;
    }

    @OnClick({R.id.tv_sure, R.id.tv_cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showShort("密码不能为空");
                    return;
                }
                if (onPasswordConfigListener != null) {
                    onPasswordConfigListener.onSure(pwd);
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public BaseDialog setOnPasswordConfigListener(InputRefundPwdDialog.onPasswordConfigListener onPasswordConfigListener) {
        this.onPasswordConfigListener = onPasswordConfigListener;
        return this;
    }

    public interface onPasswordConfigListener {
        void onSure(String pwd);
    }
}
