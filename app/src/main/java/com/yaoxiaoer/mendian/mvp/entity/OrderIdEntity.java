package com.yaoxiaoer.mendian.mvp.entity;

/**
 * Created by Chenwy on 2018/3/16.
 */

public class OrderIdEntity {
    public int code;
    public String message;
    public Data data;
    public static class Data{
        public String orderId;
    }
}
