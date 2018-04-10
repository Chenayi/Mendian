package com.yaoxiaoer.mendian.utils;

/**
 * Created by Chenwy on 2018/2/7.
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
    /**
     * 已完成
     */
    public static final int ORDER_FINISHED = 3;
    /**
     * 已取消
     */
    public static final int ORDER_CANCELED = 4;

    /**
     * 快速支付（门店自有）
     */
    public static final String SOURCE_STORE = "0";

    /**
     * 微商城
     */
    public static final String SOURCE_WSC = "1";
}
