package com.yaoxiaoer.mendian.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.widget.RootLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Chenwy on 2018/5/16.
 */
public class InputRefundPwdActivity extends BaseActivity {

    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_refund;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected void initDatas() {
        RootLayout.getInstance(this)
                .setOnRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new BackHomeEvent());
                        finish();
                    }
                });
    }

    @OnClick({R.id.btn_sure})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                if (configPassword()) {
                    ToastUtils.showShort("等待接口中...");
                }
                break;
        }
    }


    private boolean configPassword() {
        String pwd = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("请输入密码");
            return false;
        }
        return true;
    }
}
