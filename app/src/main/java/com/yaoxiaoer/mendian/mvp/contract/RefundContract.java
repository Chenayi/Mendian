package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;

/**
 * Created by Chenwy on 2018/5/16.
 */
public interface RefundContract {
    interface View extends IView {
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

        /**
         * 关闭
         */
        void close();
    }
}
