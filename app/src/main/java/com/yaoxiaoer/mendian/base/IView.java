package com.yaoxiaoer.mendian.base;

/**
 * Created by Chenwy on 2018/1/25 16:39
 */

public interface IView {

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 失败回调
     *
     * @param code
     * @param msg
     */
    void onError(int code, String msg);
}
