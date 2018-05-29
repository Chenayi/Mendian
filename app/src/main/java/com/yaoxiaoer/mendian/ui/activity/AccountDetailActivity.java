package com.yaoxiaoer.mendian.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerAccountDetailComponent;
import com.yaoxiaoer.mendian.di.module.AccountDetailModule;
import com.yaoxiaoer.mendian.event.AccountsRefundSuccessEvent;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.mvp.contract.AccountDetailContract;
import com.yaoxiaoer.mendian.mvp.entity.AccountDetailEntity;
import com.yaoxiaoer.mendian.mvp.presenter.AccountDetailPresenter;
import com.yaoxiaoer.mendian.ui.dialog.InputRefundPwdDialog;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.widget.RootLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * Created by Chenwy on 2018/2/8.
 */

/**
 * 账目详情
 */
public class AccountDetailActivity extends BaseActivity<AccountDetailPresenter> implements AccountDetailContract.View {
    @BindView(R.id.tv_gather_store)
    TextView tvStoreName;
    @BindView(R.id.tv_order_source)
    TextView tvOrderSource;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.ll_pay_type)
    LinearLayout llPayType;
    @BindView(R.id.tv_pt_order_code)
    TextView tvPtOrderCode;
    @BindView(R.id.tv_pay_order_code)
    TextView tvPayOrderCode;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.ll_order_status)
    LinearLayout llOrderStatus;
    @BindView(R.id.ll_wsc_order_code)
    LinearLayout llWscOrderCode;
    @BindView(R.id.tv_money)
    TextView tvMoney;

    private String orderId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_detail;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAccountDetailComponent.builder()
                .appComponent(appComponent)
                .accountDetailModule(new AccountDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        Bundle extras = getIntent().getExtras();
        orderId = extras.getString("orderId");
        mPresenter.requestAccountDetail(orderId);
        RootLayout.getInstance(this)
                .setOnRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showInputPwdDialog();
                    }
                });
    }

    private void showInputPwdDialog() {
        InputRefundPwdDialog.newInstance()
                .setOnPasswordConfigListener(new InputRefundPwdDialog.onPasswordConfigListener() {
                    @Override
                    public void onSure(String pwd) {
                        mPresenter.subRefund(orderId, pwd);
                    }
                })
                .setMargin(30)
                .show(getSupportFragmentManager());
    }

    @Override
    public void showLoading() {
        startLoading();
    }

    @Override
    public void hideLoading() {
        stopLoading();
    }

    @Override
    public void onError(int code, String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showAccountDetail(AccountDetailEntity.Detail detail) {
        //收款门店
        tvStoreName.setText(detail.storeName);
        //订单来源
        if (!TextUtils.isEmpty(detail.orderSource)) {
            //门店自有
            if (detail.orderSource.equals("0")) {
                tvOrderSource.setText("门店自有");
                llWscOrderCode.setVisibility(View.GONE);
            }
            //微商城
            else {
                tvOrderSource.setText("微商城");
                //订单号(微商城)
                llWscOrderCode.setVisibility(View.VISIBLE);
                tvOrderCode.setText(detail.wscOrderCode);
            }
        }

        //订单日期和时间
        String timeEndToString = detail.timeEndToString;
        if (!TextUtils.isEmpty(timeEndToString)) {
            String date = timeEndToString.split(" ")[0];
            String time = timeEndToString.split(" ")[1];
            tvOrderDate.setText(date);
            tvOrderTime.setText(time);
        }
        //平台订单号
        tvPtOrderCode.setText(detail.orderCode);
        //支付订单号
        tvPayOrderCode.setText(detail.transactionId);
        //收款渠道
        if (!TextUtils.isEmpty(detail.payType)) {
            String payType = detail.payType;
            if (payType.equals(Order.PAY_TYPE_WX)) {
                tvPayType.setText("微信支付");
            } else if (payType.equals(Order.PAY_TYPE_ALIPAY)) {
                tvPayType.setText("支付宝");
            } else if (payType.equals(Order.PAY_TYPE_CASH)) {
                tvPayType.setText("现金支付");
            }
        }
        //支付方式
        if (!TextUtils.isEmpty(detail.triggerType)) {
            String triggerType = detail.triggerType;
            // 1 APP反扫用户
            if (triggerType.equals(Order.TRIGGER_TYPE_APP_SCAN_USER)) {
                tvPayWay.setText("反扫");
            }
            // 0 用户扫码支付
            else if (triggerType.equals(Order.TRIGGER_TYPE_USER_SCAN_APP)) {
                tvPayWay.setText("二维码");
            }
            //2 固定二维码
            else if (triggerType.equals(Order.TRIGGER_TYPE_FIXED_QRCODE)) {
                tvPayWay.setText("二维码");
            }
        }

        //收款金额
        tvMoney.setText("收款金额：￥" + detail.orderPrice);
    }

    /**
     * 退款审核中
     */
    @Override
    public void verifying() {
    }

    /**
     * 退款成功
     */
    @Override
    public void refundSuccess() {
        EventBus.getDefault().post(new AccountsRefundSuccessEvent());
        ToastUtils.showLong("已成功退款");
        finish();
    }

    @Override
    protected boolean isLoadEventBus() {
        return true;
    }

    @Subscribe
    public void backHome(BackHomeEvent backHomeEvent) {
        finish();
    }
}
