package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;

/**
 * Created by Chenwy on 2018/3/16.
 */

public interface SplashContract {
    interface View extends IView {
        void startScanAnim(boolean isLogin);
    }
}
