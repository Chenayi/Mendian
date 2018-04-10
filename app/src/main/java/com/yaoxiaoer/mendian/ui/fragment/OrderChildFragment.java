package com.yaoxiaoer.mendian.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseListFragment;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.mvp.contract.OrderContract;
import com.yaoxiaoer.mendian.mvp.entity.OrderEntity;
import com.yaoxiaoer.mendian.mvp.presenter.OrderPresenter;
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

    }

    @Override
    protected int itemLayout() {
        return 0;
    }

    @Override
    protected void myHolder(BaseViewHolder helper, OrderEntity.ListBean data) {

    }

    @Override
    protected void onListItemClick(OrderEntity.ListBean data, int position) {

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

    }

    @Override
    public void showOrders(List<OrderEntity.ListBean> orders) {

    }

    @Override
    public void showMoreOrders(List<OrderEntity.ListBean> orders) {

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
