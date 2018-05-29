package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.OrderDetailEntity;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;

/**
 * Created by Chenwy on 2018/2/7.
 */

public interface OrderDetailContract {
    interface View extends IView {
        void showOrderDetails(OrderDetailEntity orderDetailEntity);

        void selfDeliverySuccess(PayResultEntity payResultEntity);

        void onSelfDeliveryError(int code, String msg);

        /**
         * 审核中
         */
        void verifying();

        /**
         * 退款成功
         */
        void refundSuccess();

        /**
         * 拒绝退款成功
         */
        void refuseRefundSuccess();
    }
}
