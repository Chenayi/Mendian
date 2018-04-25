package com.yaoxiaoer.mendian.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerPaySuccessCompoent;
import com.yaoxiaoer.mendian.di.module.PaySuccessModule;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.mvp.contract.PaySuccessContract;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.mvp.presenter.PaySuccessPresenter;
import com.yaoxiaoer.mendian.utils.Order;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付成功
 * Created by Chenwy on 2018/2/2.
 */
public class PaySuccessActivity extends BaseActivity<PaySuccessPresenter> implements PaySuccessContract.View {
    @BindView(R.id.tv_delay)
    TextView tvDelay;
    @BindView(R.id.tv_gather_money)
    TextView tvGatherMoney;
    @BindView(R.id.tv_pay_order_code)
    TextView tvPayOrderCode;
    @BindView(R.id.tv_pt_order_code)
    TextView tvPtOrderCode;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_gather_way)
    TextView tvGatherWay;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;

    /**
     * 支付信息
     */
    private PayResultEntity mPayResultEntity;
    /**
     * 是否来自订单
     */
    private boolean isFromOrder;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerPaySuccessCompoent.builder()
                .appComponent(appComponent)
                .paySuccessModule(new PaySuccessModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        Bundle extras = getIntent().getExtras();
        mPayResultEntity = (PayResultEntity) extras.getSerializable("payResult");
        isFromOrder = extras.getBoolean("isFromOrder");

        //收款方式
        String payType = mPayResultEntity.payType;
        String payTypeVoice;
        if (payType.equals(Order.PAY_TYPE_WX)) {
            tvGatherWay.setText("微信支付");
            payTypeVoice = "微信";
        } else if (payType.equals(Order.PAY_TYPE_ALIPAY)) {
            tvGatherWay.setText("支付宝");
            payTypeVoice = "支付宝";
        } else {
            tvGatherWay.setText("现金支付");
            payTypeVoice = "现金";
        }

        //语音播报
        mPresenter.playSpeech(payTypeVoice + "收款" + mPayResultEntity.orderPrice + "元");

        if (!isFromOrder) {
            tvOrderCode.setVisibility(View.GONE);
        } else {
            tvOrderCode.setVisibility(View.VISIBLE);
            //微商城订单号
            tvOrderCode.setText(mPayResultEntity.wscOrderCode);
        }

        //收款金额
        tvGatherMoney.setText("收款金额：" + mPayResultEntity.orderPrice + "元");
        //支付订单号
        tvPayOrderCode.setText(mPayResultEntity.transactionId);
        //平台订单号
        tvPtOrderCode.setText(mPayResultEntity.orderCode);
        //支付方式
        String triggerType = mPayResultEntity.triggerType;
        if (triggerType != null) {
            // 1 APP反扫用户
            if (triggerType.equals("1")) {
                tvPayWay.setText("反扫");
            }
            // 0 用户扫码支付
            else if (triggerType.equals("0")) {
                tvPayWay.setText("二维码");
            }
        } else {
            tvPayWay.setText("二维码");
        }

        //开始倒计时
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
