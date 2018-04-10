package com.yaoxiaoer.mendian.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yaoxiaoer.mendian.GlideApp;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerQrcodeComponent;
import com.yaoxiaoer.mendian.di.module.QrcodeModule;
import com.yaoxiaoer.mendian.mvp.contract.QrcodeContract;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.mvp.entity.QrcodeEntity;
import com.yaoxiaoer.mendian.mvp.presenter.QrcodePresenter;
import com.yaoxiaoer.mendian.widget.RootLayout;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;

/**
 * Created by Chenwy on 2018/2/2.
 * 正扫，即展示二维码给用户扫描
 */
public class QrcodeActivity extends BaseActivity<QrcodePresenter> implements QrcodeContract.View {
    @BindView(R.id.iv_qrcode)
    ImageView ivQrcode;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    private boolean isFromOrder;
    private String payType;
    private String orderId;
    private String orderPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerQrcodeComponent.builder()
                .appComponent(appComponent)
                .qrcodeModule(new QrcodeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        Bundle bundle = getIntent().getExtras();
        isFromOrder = bundle.getBoolean("isFromOrder");
        payType = bundle.getString("payType");
        orderId = bundle.getString("orderId");
        orderPrice = bundle.getString("orderPrice");
        tvPayMoney.setText("￥" + orderPrice);
        mPresenter.requestQrcode(isFromOrder, payType, orderPrice, orderId);
        RootLayout.getInstance(this)
                .setOnRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new BackHomeEvent());
                        finish();
                    }
                });
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
    public void showQrCode(QrcodeEntity qrcodeEntity) {
        GlideApp.with(this)
                .load(qrcodeEntity.payImage)
                .placeholder(R.color.white)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(ivQrcode);
    }

    @Override
    public void paySuccess(PayResultEntity payResultEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("payResult", payResultEntity);
        bundle.putBoolean("isFromOrder", isFromOrder);
        jumpActivity(bundle, PaySuccessActivity.class);
        finish();
    }
}
