package com.yaoxiaoer.mendian.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseListFragment;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerOrderComponent;
import com.yaoxiaoer.mendian.di.module.OrderModule;
import com.yaoxiaoer.mendian.mvp.contract.OrderContract;
import com.yaoxiaoer.mendian.mvp.entity.OrderEntity;
import com.yaoxiaoer.mendian.mvp.presenter.OrderPresenter;
import com.yaoxiaoer.mendian.ui.activity.MainActivity;
import com.yaoxiaoer.mendian.ui.activity.OrderDetailActivity;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Chenwy on 2018/4/10.
 */
public class OrderChildFragment extends BaseListFragment<OrderPresenter, OrderEntity.ListBean> implements OrderContract.View {
    /**
     * 开始时间
     */
    private String mStartDay;
    /**
     * 结束时间
     */
    private String mEndDay;
    /**
     * 订单来源 0快速支付 1微商城
     */
    private String mOrderSource = "1";
    /**
     * 订单号
     */
    private String mOrderCode;
    /**
     * ]
     * 收货人
     */
    private String mBuyMember;

    /**
     * 订单状态
     */
    private String mStatus;

    public static OrderChildFragment newInstance(int p) {
        Bundle args = new Bundle();
        args.putInt("p", p);
        OrderChildFragment fragment = new OrderChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        int p = arguments.getInt("p");
        switch (p) {
            case 1:
                mStatus = String.valueOf(Order.ORDER_NO_HANDLE);
                break;
            case 2:
                mStatus = String.valueOf(Order.ORDER_FINISHED);
                break;
            case 3:
                mStatus = String.valueOf(Order.ORDER_CANCELED);
                break;
        }

        String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        mStartDay = Utils.getStartDateTime(nowString);
        mEndDay = Utils.getEndDateTime(nowString);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_order_child;
    }

    @Override
    protected void refreshData() {
        mPresenter.requestOrders(mStatus, pageNo, C.PAGE_SIZE,
                mOrderSource, mOrderCode, mBuyMember, mStartDay, mEndDay, null);
    }

    /**
     * 刷新订单
     */
    public void refreshOrder() {
        if (isAcquired) {
            startLoading();
            hideEmptyView();
            clearAll();
            pageNo = 1;
            mPresenter.requestOrders(mStatus, pageNo, C.PAGE_SIZE,
                    mOrderSource, mOrderCode, mBuyMember, mStartDay, mEndDay, null);
        }
    }

    /**
     * 重置参数
     */
    public void resetOption() {
        String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        mStartDay = Utils.getStartDateTime(nowString);
        mEndDay = Utils.getEndDateTime(nowString);
        mOrderSource = "1";
        mOrderCode = null;
        mBuyMember = null;
    }

    /**
     * 设置参数
     *
     * @param startDay
     * @param endDay
     * @param orderSource
     * @param orderCode
     * @param buyMember
     */
    public void setOptions(String startDay, String endDay, String orderSource, String orderCode, String buyMember) {
        mStartDay = startDay;
        mEndDay = endDay;
        mOrderSource = orderSource;
        mOrderCode = orderCode;
        mBuyMember = buyMember;
    }

    @Override
    protected int itemLayout() {
        return R.layout.item_order_list;
    }

    @Override
    protected void myHolder(BaseViewHolder helper, OrderEntity.ListBean data) {
        helper.setText(R.id.tv_order_code, "订单号：" + data.orderCode)
                .setText(R.id.tv_username, data.customerName)
                .setText(R.id.tv_order_price, "￥" + data.orderPrice)
                .setGone(R.id.top_line, helper.getLayoutPosition() == 0);


        switch (data.orderStatus) {
            //未处理
            case Order.ORDER_NO_HANDLE:
                helper.setText(R.id.tv_order_status, "未处理");
                helper.setBackgroundColor(R.id.tv_order_status, ContextCompat.getColor(getContext(), R.color.color_ff9600));
                break;
            //已完成
            case Order.ORDER_FINISHED:
                helper.setText(R.id.tv_order_status, "已完成");
                helper.setBackgroundColor(R.id.tv_order_status, ContextCompat.getColor(getContext(), R.color.color_37c4a4));
                break;
            case Order.ORDER_CANCELED:
                helper.setText(R.id.tv_order_status, "已取消");
                helper.setBackgroundColor(R.id.tv_order_status, ContextCompat.getColor(getContext(), R.color.color_ff552e));
                break;
        }
    }

    @Override
    protected void onListItemClick(OrderEntity.ListBean data, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("orderId", data.orderId);
        jumpActivity(bundle, OrderDetailActivity.class);
    }

    @Override
    protected void onListItemChildClick(int viewId, OrderEntity.ListBean data, int position) {
    }

    @Override
    protected String getEmptyTips() {
        return "亲，暂无订单数据";
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerOrderComponent.builder()
                .appComponent(appComponent)
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showOrders(List<OrderEntity.ListBean> orders) {
        SPUtils.getInstance().put(C.NEW_ORDER, false);
        ((MainActivity) _mActivity).hideBadge();
        finishRefresh(orders);
    }

    @Override
    public void showMoreOrders(List<OrderEntity.ListBean> orders) {
        finishLoadMore(orders);
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
}
