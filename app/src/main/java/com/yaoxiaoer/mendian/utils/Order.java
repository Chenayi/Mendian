package com.yaoxiaoer.mendian.utils;

/**
 * Created by Chenwy on 2018/2/7.
 * 订单常量
 */

public class Order {
    public static final int START_DATE = -1;
    public static final int END_DATE = -2;
    public static final int TODAY = 0;
    public static final int YESTODAY = 1;
    public static final int _7DAY = 2;
    public static final int _30DAY = 3;

    /**
     * 支付宝
     */
    public static final String PAY_ALIPAY = "1";

    /**
     * 微信
     */
    public static final String PAY_WX = "0";

    /**
     * 微信支付
     */
    public static final String PAY_TYPE_WX = "0";
    /**
     * 支付宝支付
     */
    public static final String PAY_TYPE_ALIPAY = "1";
    /**
     * 现金支付
     */
    public static final String PAY_TYPE_CASH = "2";

    //订单状态
    /**
     * 未处理
     */
    public static final int ORDER_NO_HANDLE = 0;
    public static final int ORDER_NO_HANDLE2 = 1;
    /**
     * 已完成
     */
    public static final int ORDER_FINISHED = 3;
    /**
     * 已取消
     */
    public static final int ORDER_CANCELED = 4;
    /**
     * 退款
     */
    public static final int ORDER_REFUND = 100;

    /**
     * 拒绝退款
     */
    public static final int ORDER_REFUSE_REFUND = 13;

    /**
     * 待退款
     */
    public static final int ORDER_WAIT_REFUND = 15;

    /**
     * 退款成功
     */
    public static final int ORDER_REFUND_SUCCESS = 18;

    /**
     * 退款失败
     */
    public static final int ORDER_REFUND_FAIL = 20;


    /**
     * 快速支付（门店自有）
     */
    public static final String SOURCE_STORE = "0";

    /**
     * 微商城
     */
    public static final String SOURCE_WSC = "1";

    //触发方式
    /**
     * 用户扫码支付
     */
    public static final String TRIGGER_TYPE_USER_SCAN_APP = "0";
    /**
     * App反扫用户
     */
    public static final String TRIGGER_TYPE_APP_SCAN_USER = "1";
    /**
     * 固定二维码
     */
    public static final String TRIGGER_TYPE_FIXED_QRCODE = "2";

    /**
     * 公众号
     */
    public static final String TRIGGER_TYPE_PUBLIC_NUMBER = "3";
}
