package com.yaoxiaoer.mendian.http.exception;

import java.io.IOException;

/**
 * Created by Chenwy on 2017/10/20.
 */

public class ResultException extends RuntimeException {
    public int errorCode;
    public String errorMsg;
    public String other;

    public ResultException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ResultException(int errorCode, String errorMsg, String other) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.other = other;
    }
}
