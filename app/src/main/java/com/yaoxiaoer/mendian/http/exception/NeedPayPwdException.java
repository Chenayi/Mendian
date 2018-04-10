package com.yaoxiaoer.mendian.http.exception;

/**
 * Created by Chenwy on 2018/3/16.
 */

public class NeedPayPwdException extends ResultException {
    public String orderId;

    public NeedPayPwdException(int errorCode, String errorMsg, String orderId) {
        super(errorCode, errorMsg);
        this.orderId = orderId;
    }
}
