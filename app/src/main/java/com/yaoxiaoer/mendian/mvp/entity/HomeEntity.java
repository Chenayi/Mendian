package com.yaoxiaoer.mendian.mvp.entity;

/**
 * Created by Chenwy on 2018/2/5.
 */

public class HomeEntity {

    /**
     * oneday : {"averagePrice":0,"orderNumbers":0,"sumPrice":0}
     * storeInfo : {"prePage":1,"lastPage":true,"storeName":"看看","nextPage":1,"totalCount":0,"pageNo":1,"pageSize":10,"startRowNum":0,"firstPage":true,"updateType":0,"totalPage":1}
     * chaincompanyName : {}
     * sevenday : {"averagePrice":45.7,"orderNumbers":3,"sumPrice":137.1}
     * thirtyday : {"averagePrice":36.75,"orderNumbers":4,"sumPrice":147}
     */

    private OnedayBean oneday;
    private StoreInfoBean storeInfo;
    private ChaincompanyNameBean chaincompanyName;
    private SevendayBean sevenday;
    private ThirtydayBean thirtyday;

    public OnedayBean getOneday() {
        return oneday;
    }

    public void setOneday(OnedayBean oneday) {
        this.oneday = oneday;
    }

    public StoreInfoBean getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfoBean storeInfo) {
        this.storeInfo = storeInfo;
    }

    public ChaincompanyNameBean getChaincompanyName() {
        return chaincompanyName;
    }

    public void setChaincompanyName(ChaincompanyNameBean chaincompanyName) {
        this.chaincompanyName = chaincompanyName;
    }

    public SevendayBean getSevenday() {
        return sevenday;
    }

    public void setSevenday(SevendayBean sevenday) {
        this.sevenday = sevenday;
    }

    public ThirtydayBean getThirtyday() {
        return thirtyday;
    }

    public void setThirtyday(ThirtydayBean thirtyday) {
        this.thirtyday = thirtyday;
    }

    public static class OnedayBean {
        /**
         * averagePrice : 0
         * orderNumbers : 0
         * sumPrice : 0
         */

        private String averagePrice;
        private int orderNumbers;
        private String sumPrice;

        public String getAveragePrice() {
            return averagePrice;
        }

        public void setAveragePrice(String averagePrice) {
            this.averagePrice = averagePrice;
        }

        public int getOrderNumbers() {
            return orderNumbers;
        }

        public void setOrderNumbers(int orderNumbers) {
            this.orderNumbers = orderNumbers;
        }

        public String getSumPrice() {
            return sumPrice;
        }

        public void setSumPrice(String sumPrice) {
            this.sumPrice = sumPrice;
        }
    }

    public static class StoreInfoBean {
        private String storeName;

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }
    }

    public static class ChaincompanyNameBean {
    }

    public static class SevendayBean {
        /**
         * averagePrice : 45.7
         * orderNumbers : 3
         * sumPrice : 137.1
         */

        private String averagePrice;
        private int orderNumbers;
        private String sumPrice;

        public String getAveragePrice() {
            return averagePrice;
        }

        public void setAveragePrice(String averagePrice) {
            this.averagePrice = averagePrice;
        }

        public int getOrderNumbers() {
            return orderNumbers;
        }

        public void setOrderNumbers(int orderNumbers) {
            this.orderNumbers = orderNumbers;
        }

        public String getSumPrice() {
            return sumPrice;
        }

        public void setSumPrice(String sumPrice) {
            this.sumPrice = sumPrice;
        }
    }

    public static class ThirtydayBean {
        /**
         * averagePrice : 36.75
         * orderNumbers : 4
         * sumPrice : 147
         */

        private String averagePrice;
        private int orderNumbers;
        private String sumPrice;

        public String getAveragePrice() {
            return averagePrice;
        }

        public void setAveragePrice(String averagePrice) {
            this.averagePrice = averagePrice;
        }

        public int getOrderNumbers() {
            return orderNumbers;
        }

        public void setOrderNumbers(int orderNumbers) {
            this.orderNumbers = orderNumbers;
        }

        public String getSumPrice() {
            return sumPrice;
        }

        public void setSumPrice(String sumPrice) {
            this.sumPrice = sumPrice;
        }
    }
}
