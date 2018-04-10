package com.yaoxiaoer.mendian.mvp.entity;

/**
 * Created by Chenwy on 2018/2/1.
 */

public class BaseResponse<T> {
    public int code;
    public String message;
    public T data;
}
