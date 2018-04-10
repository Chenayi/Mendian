package com.yaoxiaoer.mendian.ui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseTitleBarListActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerOrderDetailComponent;
import com.yaoxiaoer.mendian.di.module.OrderDetailModule;
import com.yaoxiaoer.mendian.mvp.contract.OrderDetailContract;
import com.yaoxiaoer.mendian.mvp.entity.OrderDetailEntity;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.mvp.presenter.OrderDetailPresenter;
import com.yaoxiaoer.mendian.ui.dialog.TipsDialog;
import com.yaoxiaoer.mendian.utils.Order;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 订单详情
 * Created by Chenwy on 2018/2/2.
 */

public class OrderDetailActivity extends BaseTitleBarListActivity<OrderDetailPresenter, OrderDetailEntity.OrderGoodsListBean> implements OrderDetailContract.View {
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private View header1;
    private View foot;
    private TextView tvOrderCode;
    private TextView tvOrderSource;
    private TextView tvPayWay;
    private TextView tvDistributionType;
    private TextView tvCusName;
    private TextView tvPhoneNumber;
    private TextView tvAddress;
    private TextView tvOrderStatus;
    private TextView tvPayCountMoney;
    private LinearLayout llPayType;
    private TextView tvPayType;

    private int mOrderId;
    private String mOrderPrice;
    private List<OrderDetailEntity.OrderGoodsListBean> mOrderGoodsList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerOrderDetailComponent.builder()
                .appComponent(appComponent)
                .orderDetailModule(new OrderDetailModule(this))
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
        mOrderId = bundle.getInt("orderId");
    }

    @Override
    protected void initHeaderAndFooterView() {
        header1 = getHeaderOrFooterView(true, R.layout.header_order_detail1);
        getHeaderOrFooterView(true, R.layout.header_order_detail2);
        foot = getHeaderOrFooterView(false, R.layout.foot_order_detail);
        tvOrderCode = header1.findViewById(R.id.tv_order_code);
        tvOrderSource = header1.findViewById(R.id.tv_order_source);
        tvPayWay = header1.findViewById(R.id.tv_pay_way);
        tvDistributionType = header1.findViewById(R.id.tv_distributionType);
        tvCusName = header1.findViewById(R.id.tv_cus_name);
        tvPhoneNumber = header1.findViewById(R.id.tv_phone_number);
        tvAddress = header1.findViewById(R.id.tv_address);
        tvOrderStatus = header1.findViewById(R.id.tv_order_status);
        llPayType = header1.findViewById(R.id.ll_pay_type);
        tvPayType = header1.findViewById(R.id.tv_pay_type);
        tvPayCountMoney = foot.findViewById(R.id.tv_pay_count_money);
    }

    @Override
    protected String getBarTitle() {
        return "订单详情";
    }

    @Override
    protected void refreshData() {
        mPresenter.requestOrderDetail(mOrderId);
    }

    @Override
    protected int itemLayout() {
        return R.layout.item_order_detail_goods;
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

    @OnClick({R.id.btn_gathering, R.id.btn_self_take})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gathering:
                Bundle bundle = new Bundle();
                bundle.putString("orderId", mOrderId + "");
                bundle.putString("orderPrice", mOrderPrice);
                bundle.putSerializable("orderGoods", (Serializable) mOrderGoodsList);
                jumpActivity(bundle, GatheringWithOrderActivity.class);
                break;
            case R.id.btn_self_take:
                TipsDialog.newInstance("注意说明", "自提发货等同于该订单已现金支付完成" +
                        "是否已收到货款现金？", ContextCompat.getColor(this, R.color.color_ff9600))
                        .setOnTipsOnClickListener(new TipsDialog.OnTipsOnClickListener() {
                            @Override
                            public void onSure() {
                                mPresenter.selfDelivery(String.valueOf(mOrderId));
                            }

                            @Override
                            public void onCancel() {

                            }
                        })
                        .show(getSupportFragmentManager());
                break;
        }
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
    public void showLoading() {
        startLoading();
    }

    @Override
    public void hideLoading() {
        stopLoading();
    }

    @Override
    public void onError(int code, String msg) {
        handleError(code, msg);
    }

    @Override
    public void showOrderDetails(OrderDetailEntity orderDetailEntity) {
        mOrderGoodsList = orderDetailEntity.getOrderGoodsList();
        finishRefresh(mOrderGoodsList);

        OrderDetailEntity.OrderDetailBean orderDetail = orderDetailEntity.getOrderDetail();
        mOrderPrice = orderDetail.getOrderPrice();
        //订单号
        if (!TextUtils.isEmpty(orderDetail.getOrderCode())){
            tvOrderCode.setText(orderDetail.getOrderCode());
        }
        //订单来源
        if (!TextUtils.isEmpty(orderDetail.getOrderSource())){
            tvOrderSource.setText(orderDetail.getOrderSource().equals("0") ? "快速支付" : "微商城");
        }
        //支付方式
        if (!TextUtils.isEmpty(orderDetail.paymentMethod)) {
            tvPayWay.setText(orderDetail.paymentMethod.equals("1") ? "线上支付" : "线下支付");
        }
        //配送方式
        if (!TextUtils.isEmpty(orderDetail.getDistributionType())){
            tvDistributionType.setText(orderDetail.getDistributionType().equals("1") ? "快递配送" : "上门自提");
        }

        //客户姓名
        if (!TextUtils.isEmpty(orderDetail.getCustomerName())){
            tvCusName.setText(orderDetail.getCustomerName());
        }
        //手机号码
        if (!TextUtils.isEmpty(orderDetail.getCustomerPhone())){
            tvPhoneNumber.setText(orderDetail.getCustomerPhone());
        }
        //自提地址
        tvAddress.setText(TextUtils.isEmpty(orderDetail.storeAddress) ? "无" : orderDetail.storeAddress);
        //订单状态
        //已完成
        if (orderDetail.orderStatus == Order.ORDER_FINISHED) {
            llBottom.setVisibility(View.GONE);
            tvOrderStatus.setVisibility(View.VISIBLE);
            llPayType.setVisibility(View.VISIBLE);
            tvOrderStatus.setText("订单已完成");
            tvOrderStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            if (orderDetail.payType != null) {
                setPayType(orderDetail.payType);
            }
        }
        //已取消
        else if (orderDetail.orderStatus == Order.ORDER_CANCELED) {
            llBottom.setVisibility(View.GONE);
            tvOrderStatus.setVisibility(View.VISIBLE);
            tvOrderStatus.setText("订单已取消");
            tvOrderStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.color_ff552e));
            llPayType.setVisibility(View.VISIBLE);
            if (orderDetail.payType != null) {
                setPayType(orderDetail.payType);
            }
        }
        //未处理
        else {
            llBottom.setVisibility(View.VISIBLE);
            tvOrderStatus.setVisibility(View.GONE);
            llPayType.setVisibility(View.GONE);
        }

        tvPayCountMoney.setText("实付款：￥" + mOrderPrice);
    }

    /**
     * 收款渠道
     */
    private void setPayType(String payType) {
        //微信
        if (payType.equals(Order.PAY_WX)) {
            tvPayType.setText("微信");
        }
        //支付宝
        else if (payType.equals(Order.PAY_TYPE_ALIPAY)) {
            tvPayType.setText("支付宝");
        }
        //现金
        else if (payType.equals(Order.PAY_TYPE_CASH)) {
            tvPayType.setText("现金支付");
        }
    }

    @Override
    public void selfDeliverySuccess(PayResultEntity payResultEntity) {
        Bundle bundle = new Bundle();
        bundle.putString("orderCode", payResultEntity.orderCode);
        jumpActivity(bundle, SelfDeliveryActivity.class);
    }

    @Override
    public void onSelfDeliveryError(int code, String msg) {
        ToastUtils.showShort(msg);
    }
}
