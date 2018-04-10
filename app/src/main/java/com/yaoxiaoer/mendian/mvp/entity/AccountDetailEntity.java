package com.yaoxiaoer.mendian.mvp.entity;

/**
 * Created by Chenwy on 2018/2/8.
 */

public class AccountDetailEntity {
    public Detail detail;

    public static class Detail{
        public String wscOrderCode;
        public String transactionId;
        public String storeName;
        public String orderSource;
        public String orderCode;
        public String payType;
        public String orderPrice;
        public String timeEndToString;
        public String triggerType;
        public String orderId;
    }
}
