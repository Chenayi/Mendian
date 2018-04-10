package com.yaoxiaoer.mendian.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerSelfDeliveryComponent;
import com.yaoxiaoer.mendian.di.module.SelfDeliveryModule;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.mvp.contract.SelfDeliveryContract;
import com.yaoxiaoer.mendian.mvp.presenter.SelfDeliceryPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 自提发货（结果页）
 * Created by Chenwy on 2018/2/7.
 */

public class SelfDeliveryActivity extends BaseActivity<SelfDeliceryPresenter> implements SelfDeliveryContract.View {
    @BindView(R.id.tv_delay)
    TextView tvDelay;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;


    /**
     * 传过来的订单号
     */
    private String mOrderCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_self_delivery;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerSelfDeliveryComponent.builder()
                .appComponent(appComponent)
                .selfDeliveryModule(new SelfDeliveryModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        Bundle bundle = getIntent().getExtras();
        mOrderCode = bundle.getString("orderCode");
        tvOrderCode.setText("订单号：" + mOrderCode == null ? "" : mOrderCode);
        mPresenter.delayFinish(5);
    }


    @Override
    public void updateSecond(int second) {
        tvDelay.setText(second + "秒后自动返回首页");
    }

    @Override
    public void close() {
        EventBus.getDefault().post(new BackHomeEvent());
        finish();
    }

    @OnClick({R.id.btn_close})
    public void onClick(View v) {
        if (v.getId() == R.id.btn_close) {
            mPresenter.disposable();
            close();
        }
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(int code, String msg) {
    }
}
