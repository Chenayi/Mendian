package com.yaoxiaoer.mendian.mvp.entity;

import java.io.Serializable;

/**
 * Created by Chenwy on 2018/2/11.
 */

public class PayResultEntity implements Serializable {
    private static final long serialVersionUID = 8037418480409100054L;
    /**
     * 订单状态
     */
    public int orderStatus;
    /**
     * 订单收款的订单号
     */
    public String orderCode;
    /**
     * 无订单收款的订单号
     */
    public String orderId;

    public String transactionId;

    public String payType;

    public String orderPrice;

    public String triggerType;

    public String wscOrderCode;
}
