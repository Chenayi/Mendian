package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;

/**
 * Created by Chenwy on 2018/2/11.
 */

public interface GatheringOrderContract {
    interface View extends IView {
        void showLoadingNotBackDismiss();

        void scanPaySuccess(PayResultEntity payResultEntity);
    }
}
