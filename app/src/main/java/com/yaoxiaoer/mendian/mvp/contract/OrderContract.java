package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.OrderEntity;

import java.util.List;

/**
 * Created by Chenwy on 2018/2/1.
 */

public interface OrderContract {
    interface View extends IView {
        void showOrders(List<OrderEntity.ListBean> orders);

        void showMoreOrders(List<OrderEntity.ListBean> orders);
    }
}
