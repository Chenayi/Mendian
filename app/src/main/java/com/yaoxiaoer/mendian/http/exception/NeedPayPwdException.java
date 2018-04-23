package com.yaoxiaoer.mendian.http.exception;

/**
 * Created by Chenwy on 2018/3/16.
 */

public class NeedPayPwdException extends RuntimeException {
    public String orderId;
    public int errorCode;
    public String errorMsg;

    public NeedPayPwdException(int errorCode, String errorMsg, String orderId) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.orderId = orderId;
    }
}
