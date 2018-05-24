package com.yaoxiaoer.mendian.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerRefundComponent;
import com.yaoxiaoer.mendian.di.module.RefundModule;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.event.BackToAccountsEvent;
import com.yaoxiaoer.mendian.mvp.contract.RefundContract;
import com.yaoxiaoer.mendian.mvp.presenter.RefundPresenter;
import com.yaoxiaoer.mendian.ui.dialog.SimpleDialog;
import com.yaoxiaoer.mendian.widget.RootLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 输入退款密码
 * Created by Chenwy on 2018/5/16.
 */
public class InputRefundPwdActivity extends BaseActivity<RefundPresenter> implements RefundContract.View {

    @BindView(R.id.et_password)
    EditText etPassword;

    private String orderId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_refund;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerRefundComponent.builder()
                .appComponent(appComponent)
                .refundModule(new RefundModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        orderId = getIntent().getExtras().getString("orderId");
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
                String pwd = etPassword.getText().toString().trim();
                if (configPassword(pwd)) {
                    mPresenter.subRefund(orderId, pwd);
                }
                break;
        }
    }


    /**
     * 本地校验密码
     *
     * @return
     */
    private boolean configPassword(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort("请输入密码");
            return false;
        }
        return true;
    }

    @Override
    public void verifying() {

    }

    @Override
    public void refundSuccess() {
        SimpleDialog.newInstance("已成功退款")
                .setOnBackListener(new SimpleDialog.OnBackListener() {
                    @Override
                    public void onBack() {
                        mPresenter.disposable();
                        close();
                    }
                })
                .setMargin(52)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
        mPresenter.delayFinish(2);
    }

    @Override
    public void close() {
        EventBus.getDefault().post(new BackToAccountsEvent());
        finish();
    }

    @Override
    public void showLoading() {
        startLoading(false);
    }

    @Override
    public void hideLoading() {
        stopLoading();
    }

    @Override
    public void onError(int code, String msg) {
        ToastUtils.showShort(msg);
    }
}
