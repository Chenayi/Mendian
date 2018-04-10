package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.ApkInfoEntity;

/**
 * Created by Chenwy on 2018/3/1.
 */

public interface MainContract {
    interface View extends IView {
        void upgrade(ApkInfoEntity.Detail detail);
    }
}
