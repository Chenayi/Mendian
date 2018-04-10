package com.yaoxiaoer.mendian.ui.fragment;

import com.chad.library.adapter.base.BaseViewHolder;
import com.yaoxiaoer.mendian.base.BaseListFragment;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.mvp.contract.OrderContract;
import com.yaoxiaoer.mendian.mvp.entity.OrderEntity;
import com.yaoxiaoer.mendian.mvp.presenter.OrderPresenter;

import java.util.List;

/**
 * Created by Chenwy on 2018/4/10.
 */
public class OrderChildFragment extends BaseListFragment<OrderPresenter, OrderEntity.ListBean> implements OrderContract.View {
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
        return null;
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
