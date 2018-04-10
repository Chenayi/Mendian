package com.yaoxiaoer.mendian.mvp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Chenwy on 2018/2/2.
 */

public class OrderDetailEntity implements Serializable {


    private static final long serialVersionUID = -5116650795002944880L;
    /**
     * orderDetail : {"customerName":"张订单测试","distributionType":"0","orderSource":"1","orderCode":"201802061758010279","orderPrice":168,"customerPhone":"18819446959","orderId":584}
     * orderGoodsList : [{"id":642,"goodsSumPrice":168,"goodsPrice":168,"goodsNum":1,"orderId":584,"goodsName":"惠普生 典奥牌胡萝卜素软胶囊"}]
     */

    private OrderDetailBean orderDetail;
    private List<OrderGoodsListBean> orderGoodsList;

    public OrderDetailBean getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailBean orderDetail) {
        this.orderDetail = orderDetail;
    }

    public List<OrderGoodsListBean> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoodsListBean> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public static class OrderDetailBean implements Serializable {
        private static final long serialVersionUID = 2529344014550219117L;
        /**
         * customerName : 张订单测试
         * distributionType : 0
         * orderSource : 1
         * orderCode : 201802061758010279
         * orderPrice : 168
         * customerPhone : 18819446959
         * orderId : 584
         */

        private String customerName;
        private String distributionType;
        private String orderSource;
        private String orderCode;
        private String orderPrice;
        private String customerPhone;
        private int orderId;
        public int orderStatus;
        public String storeAddress;
        public String paymentMethod;
        public String payType;

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getDistributionType() {
            return distributionType;
        }

        public void setDistributionType(String distributionType) {
            this.distributionType = distributionType;
        }

        public String getOrderSource() {
            return orderSource;
        }

        public void setOrderSource(String orderSource) {
            this.orderSource = orderSource;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }
    }

    public static class OrderGoodsListBean implements Serializable {
        private static final long serialVersionUID = -2713722801983696102L;
        /**
         * id : 642
         * goodsSumPrice : 168
         * goodsPrice : 168
         * goodsNum : 1
         * orderId : 584
         * goodsName : 惠普生 典奥牌胡萝卜素软胶囊
         */

        private int id;
        private String goodsSumPrice;
        private String goodsPrice;
        private int goodsNum;
        private int orderId;
        private String goodsName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsSumPrice() {
            return goodsSumPrice;
        }

        public void setGoodsSumPrice(String goodsSumPrice) {
            this.goodsSumPrice = goodsSumPrice;
        }

        public String getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(String goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

    }
}
