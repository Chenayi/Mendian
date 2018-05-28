package com.yaoxiaoer.mendian.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseTitleBarListActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerOrderDetailComponent;
import com.yaoxiaoer.mendian.di.module.OrderDetailModule;
import com.yaoxiaoer.mendian.event.OrderStatusChangeEvent;
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
    @BindView(R.id.btn_left)
    Button btnLeft;
    @BindView(R.id.btn_right)
    Button btnRight;

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
    private int mOrderStatus;
    private String mOrderPrice;
    private String mPayType;
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
        mOrderStatus = bundle.getInt("orderStatus");
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
        //如果是未处理，去查一下是否已完成
        if (mOrderStatus == Order.ORDER_NO_HANDLE) {
            mPresenter.requestPayResult(mOrderId);
        } else {
            mPresenter.requestOrderDetail(mOrderId);
        }
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

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onClick(View v) {
        Bundle bundle;
        switch (v.getId()) {
            case R.id.btn_left:
                //确认退款
                bundle = new Bundle();
                bundle.putString("orderId", mOrderId + "");
                jumpActivityForResult(122, bundle, InputRefundPwdActivity.class);
                break;
            case R.id.btn_right:
                if (mOrderStatus == Order.ORDER_NO_HANDLE || mOrderStatus == Order.ORDER_NO_HANDLE2
                        || mOrderStatus == Order.ORDER_REFUSE_REFUND) {
                    showPickupTips();
                }
                //拒绝退款
                else if (mOrderStatus == Order.ORDER_WAIT_REFUND || mOrderStatus == Order.ORDER_REFUND_FAIL) {
                    bundle = new Bundle();
                    bundle.putString("orderId", mOrderId + "");
                    bundle.putBoolean("isRefuse", true);
                    jumpActivityForResult(122, bundle, InputRefundPwdActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //退款成功
        if (requestCode == 122 && resultCode == RESULT_OK) {
            mOrderStatus = Order.ORDER_REFUND_SUCCESS;
            orderRefundSuccess();
            EventBus.getDefault().post(new OrderStatusChangeEvent());
        }
        //拒绝退款
        else if (requestCode == 122 && resultCode == 222) {
            mOrderStatus = Order.ORDER_REFUSE_REFUND;
            orderWaitPickup();
            EventBus.getDefault().post(new OrderStatusChangeEvent());
        }
    }

    /**
     * 取货提示
     */
    private void showPickupTips() {
        TipsDialog.newInstance("注意说明",
                "确认取货操作不可还原，请确认已收款并将商品交给用户！",
                ContextCompat.getColor(this, R.color.color_ff9600),
                "是",
                "否")
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
        mPayType = orderDetail.payType;
        //订单号
        if (!TextUtils.isEmpty(orderDetail.getOrderCode())) {
            tvOrderCode.setText(orderDetail.getOrderCode());
        }
        //订单来源
        if (!TextUtils.isEmpty(orderDetail.getOrderSource())) {
            tvOrderSource.setText(orderDetail.getOrderSource().equals("0") ? "快速支付" : "微商城");
        }
        //支付方式
        if (!TextUtils.isEmpty(orderDetail.paymentMethod)) {
            tvPayWay.setText(orderDetail.paymentMethod.equals("1") ? "线上支付" : "线下支付");
        }
        //配送方式
        if (!TextUtils.isEmpty(orderDetail.getDistributionType())) {
            tvDistributionType.setText(orderDetail.getDistributionType().equals("0") ? "快递配送" : "上门自提");
        }

        //客户姓名
        if (!TextUtils.isEmpty(orderDetail.getCustomerName())) {
            tvCusName.setText(orderDetail.getCustomerName());
        }
        //手机号码
        if (!TextUtils.isEmpty(orderDetail.getCustomerPhone())) {
            tvPhoneNumber.setText(orderDetail.getCustomerPhone());
        }
        //自提地址
        tvAddress.setText(TextUtils.isEmpty(orderDetail.storeAddress) ? "无" : orderDetail.storeAddress);
        //订单状态
        mOrderStatus = orderDetail.orderStatus;
        switch (mOrderStatus) {
            //未处理
            case Order.ORDER_NO_HANDLE:
            case Order.ORDER_NO_HANDLE2:
                orderWaitPickup();
                break;
            //已取消
            case Order.ORDER_CANCELED:
                llBottom.setVisibility(View.GONE);
                tvOrderStatus.setVisibility(View.VISIBLE);
                tvOrderStatus.setText("订单已取消");
                tvOrderStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.color_ff552e));
                tvPayCountMoney.setTextColor(ContextCompat.getColor(this, R.color.color_ff552e));
                if (!TextUtils.isEmpty(orderDetail.payType)) {
                    llPayType.setVisibility(View.VISIBLE);
                    setPayType(orderDetail.payType);
                } else {
                    llPayType.setVisibility(View.GONE);
                }
                break;
            //订单已完成
            case Order.ORDER_FINISHED:
                orderFinish();
                break;
            //退款失败
            case Order.ORDER_REFUND_FAIL:
                orderWaitRefund();
                break;
            //退款成功
            case Order.ORDER_REFUND_SUCCESS:
                orderRefundSuccess();
                break;
            //拒绝退款
            case Order.ORDER_REFUSE_REFUND:
                orderWaitPickup();
                break;
            //待退款
            case Order.ORDER_WAIT_REFUND:
                orderWaitRefund();
                break;
        }

        tvPayCountMoney.setText("实付款：￥" + mOrderPrice);
    }

    /**
     * 订单待退款
     */
    private void orderWaitRefund() {
        tvOrderStatus.setVisibility(View.GONE);
        llBottom.setVisibility(View.VISIBLE);
        btnLeft.setVisibility(View.VISIBLE);
        btnRight.setVisibility(View.VISIBLE);
        btnLeft.setText("确认退款");
        btnRight.setText("拒绝退款");

        tvPayCountMoney.setTextColor(ContextCompat.getColor(this, R.color.color_ff9600));
        if (!TextUtils.isEmpty(mPayType)) {
            llPayType.setVisibility(View.VISIBLE);
            setPayType(mPayType);
        } else {
            llPayType.setVisibility(View.GONE);
        }
    }

    /**
     * 订单待确认取货
     */
    private void orderWaitPickup() {
        tvPayCountMoney.setTextColor(ContextCompat.getColor(this, R.color.color_ff9600));
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("确认取货");
        llBottom.setVisibility(View.VISIBLE);
        tvOrderStatus.setVisibility(View.GONE);
        llPayType.setVisibility(View.GONE);
    }

    /**
     * 订单退款成功
     */
    private void orderRefundSuccess() {
        llBottom.setVisibility(View.GONE);
        tvOrderStatus.setVisibility(View.VISIBLE);
        tvOrderStatus.setText("订单退款成功");
        tvOrderStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        tvPayCountMoney.setTextColor(ContextCompat.getColor(this, R.color.red));
        if (!TextUtils.isEmpty(mPayType)) {
            llPayType.setVisibility(View.VISIBLE);
            setPayType(mPayType);
        } else {
            llPayType.setVisibility(View.GONE);
        }
    }

    /**
     * 订单已完成
     */
    private void orderFinish() {
        llBottom.setVisibility(View.GONE);
        tvOrderStatus.setVisibility(View.VISIBLE);
        tvOrderStatus.setText("订单已完成");
        tvOrderStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tvPayCountMoney.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (!TextUtils.isEmpty(mPayType)) {
            llPayType.setVisibility(View.VISIBLE);
            setPayType(mPayType);
        } else {
            llPayType.setVisibility(View.GONE);
        }
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
        mOrderStatus = Order.ORDER_FINISHED;
        orderFinish();
        EventBus.getDefault().post(new OrderStatusChangeEvent());
    }

    @Override
    public void onSelfDeliveryError(int code, String msg) {
        ToastUtils.showShort(msg);
    }
}
