package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;

import java.io.File;

/**
 * Created by Chenwy on 2018/3/8.
 */

public interface UpgradeContract {
    interface View extends IView{
        void downLoadSuccess(File apkFile);
    }
}
