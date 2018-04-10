package com.yaoxiaoer.mendian.base;

/**
 * Created by Chenwy on 2018/1/25 16:41
 */

public interface IPresenter {
    /**
     * 做一些初始化工作
     */
    void onStart();

    /**
     * 做一些销毁工作
     */
    void onDestory();
}
