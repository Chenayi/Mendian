package com.yaoxiaoer.mendian.mvp.entity;

import java.util.List;

/**
 * Created by Chenwy on 2018/1/31.
 */

public class OrderEntity {
    public String pageNo;
    public List<ListBean> list;

    public static class ListBean {
        public String customerName;
        public String orderCode;
        public String orderPrice;
        public String submitOrderTimetoString;
        public int orderStatus;
        public int orderId;
    }
}
