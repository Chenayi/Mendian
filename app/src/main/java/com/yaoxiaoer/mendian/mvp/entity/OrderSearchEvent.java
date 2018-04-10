package com.yaoxiaoer.mendian.mvp.entity;

/**
 * Created by Chenwy on 2018/2/6.
 */

public class OrderSearchEvent {
    public String startDay;
    public String endDate;
    public String source;
    public String orderNum;
    public String buyMember;

    public OrderSearchEvent(String startDay, String endDate, String source, String orderNum, String buyMember) {
        this.startDay = startDay;
        this.endDate = endDate;
        this.source = source;
        this.orderNum = orderNum;
        this.buyMember = buyMember;
    }
}
