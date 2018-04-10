package com.yaoxiaoer.mendian.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseTitleBarListActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerGatheringOrderComponent;
import com.yaoxiaoer.mendian.di.module.GatheringOrderModule;
import com.yaoxiaoer.mendian.mvp.contract.GatheringOrderContract;
import com.yaoxiaoer.mendian.mvp.entity.OrderDetailEntity;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.mvp.presenter.GatheringOrderPresenter;
import com.yaoxiaoer.mendian.ui.dialog.TipsDialog;
import com.yaoxiaoer.mendian.utils.Order;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.List;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by Chenwy on 2018/2/2.
 * 订单收款
 */
@RuntimePermissions
public class GatheringWithOrderActivity extends BaseTitleBarListActivity<GatheringOrderPresenter,
        OrderDetailEntity.OrderGoodsListBean>
        implements GatheringOrderContract.View, View.OnClickListener {
    private EditText etMoney;
    private RadioGroup rgChannel;
    private RadioGroup rgScanWay;
    private Button btnSure;
    private TextView tvPayCountMoney;

    private final int REQUEST_SCAN_CODE = 1233;
    private final int REQUEST_CAMERA = 123;
    private String mOrderId;
    private String mOrderPrice;
    private List<OrderDetailEntity.OrderGoodsListBean> mOrderGoods;

    /**
     * 默认支付方式：支付宝
     */
    private String payType = Order.PAY_ALIPAY;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerGatheringOrderComponent.builder()
                .appComponent(appComponent)
                .gatheringOrderModule(new GatheringOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        super.initDatas();

        setOnRightClickListener(R.mipmap.go_home_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new BackHomeEvent());
            }
        });
    }

    @Override
    protected void initBundleDatas(Bundle bundle) {
        mOrderId = bundle.getString("orderId");
        mOrderPrice = bundle.getString("orderPrice");
        mOrderGoods = (List<OrderDetailEntity.OrderGoodsListBean>) bundle.getSerializable("orderGoods");
    }

    @Override
    protected void initHeaderAndFooterView() {
        View header1 = getHeaderOrFooterView(true, R.layout.header_gather);
        getHeaderOrFooterView(true, R.layout.header_order_detail2);
        View foot = getHeaderOrFooterView(false, R.layout.foot_order_detail);
        etMoney = header1.findViewById(R.id.et_money);
        etMoney.setText("￥" + mOrderPrice);
        etMoney.setFocusable(false);
        etMoney.setFocusableInTouchMode(false);
        etMoney.setEnabled(false);

        rgChannel = header1.findViewById(R.id.rg_channel);
        rgChannel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.way_zfb) {
                    payType = Order.PAY_ALIPAY;
                    ((RadioButton) rgScanWay.getChildAt(0)).setText("支付宝二维码");
                } else if (checkedId == R.id.way_wx) {
                    payType = Order.PAY_WX;
                    ((RadioButton) rgScanWay.getChildAt(0)).setText("微信二维码");
                }
            }
        });

        rgScanWay = header1.findViewById(R.id.rg_scan_way);
        btnSure = header1.findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);

        tvPayCountMoney = foot.findViewById(R.id.tv_pay_count_money);
        tvPayCountMoney.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tvPayCountMoney.setText("实付款：￥" + mOrderPrice);
    }

    @Override
    protected void refreshData() {
        hideLoading();
        finishRefresh(mOrderGoods);
    }

    @Override
    protected String getBarTitle() {
        return "收款";
    }

    @Override
    protected boolean enableEmptyView() {
        return false;
    }

    @Override
    protected boolean enableRefresh() {
        return false;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }


    @Override
    protected int itemLayout() {
        return R.layout.item_order_detail_goods;
    }

    @Override
    protected void myHolder(BaseViewHolder helper, OrderDetailEntity.OrderGoodsListBean data) {
        helper.setText(R.id.tv_goods_name, data.getGoodsName())
                .setText(R.id.tv_goods_price, "￥" + data.getGoodsSumPrice())
                .setText(R.id.tv_goods_num, "x" + data.getGoodsNum())
                .setGone(R.id.line, helper.getLayoutPosition() < getDataSize());
    }

    @Override
    protected void onListItemClick(OrderDetailEntity.OrderGoodsListBean data, int position) {

    }

    @Override
    protected void onListItemChildClick(int viewId, OrderDetailEntity.OrderGoodsListBean data, int position) {

    }

    @Override
    protected String getEmptyMsg() {
        return "暂无相关数据";
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        jumpActivityForResult(REQUEST_SCAN_CODE, ScanActivity.class);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        TipsDialog.newInstance(null, "需要相机权限")
                .setOnTipsOnClickListener(new TipsDialog.OnTipsOnClickListener() {
                    @Override
                    public void onSure() {
                        request.proceed();
                    }

                    @Override
                    public void onCancel() {
                        request.cancel();
                    }
                })
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        GatheringWithOrderActivityPermissionsDispatcher.showCameraWithPermissionCheck(this);
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        TipsDialog.newInstance(null, "请到设置中打开相机权限")
                .setOnTipsOnClickListener(new TipsDialog.OnTipsOnClickListener() {
                    @Override
                    public void onSure() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }

                    @Override
                    public void onCancel() {
                    }
                })
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GatheringWithOrderActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sure:
                if (!((RadioButton) rgScanWay.getChildAt(1)).isChecked()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isFromOrder", true);
                    bundle.putString("orderId", mOrderId);
                    bundle.putString("orderPrice", mOrderPrice);
                    bundle.putString("payType", payType);
                    jumpActivity(bundle, QrcodeActivity.class);
                } else {
                    GatheringWithOrderActivityPermissionsDispatcher.showCameraWithPermissionCheck(this);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    mPresenter.requestScanPay(result, mOrderId, mOrderPrice);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.showShort("解析二维码失败");
                }
            }
        }
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
    protected boolean isLoadEventBus() {
        return true;
    }

    @Subscribe
    public void paySuccess(BackHomeEvent backHomeEvent) {
        finish();
    }

    @Override
    public void scanPaySuccess(PayResultEntity payResultEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("payResult", payResultEntity);
        bundle.putBoolean("isFromOrder", true);
        jumpActivity(bundle, PaySuccessActivity.class);
    }

    @Override
    public void paySuccess(PayResultEntity payResultEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("payResult", payResultEntity);
        bundle.putBoolean("isFromOrder", true);
        jumpActivity(bundle, PaySuccessActivity.class);
    }
}
