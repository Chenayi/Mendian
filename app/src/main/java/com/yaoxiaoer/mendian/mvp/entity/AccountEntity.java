package com.yaoxiaoer.mendian.mvp.entity;

import java.util.List;

/**
 * Created by Chenwy on 2018/2/8.
 */

public class AccountEntity {
    public List<Account> list;
    public Sum sum;

    public static class Sum {
        public String orderNumbers;
        public String sumPrice;
    }

    public static class Account {
        public String wscOrderCode;
        public String orderSource;
        public String orderCode;
        public String unickname;
        public String payType;
        public String orderPrice;
        public String timeEndToString;
        public String orderId;
        public int orderStatus;
    }
}
