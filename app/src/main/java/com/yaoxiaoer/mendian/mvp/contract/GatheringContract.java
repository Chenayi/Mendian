package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;

/**
 * Created by Chenwy on 2018/2/12.
 */

public interface GatheringContract {
    interface View extends IView {

        void showLoadingNotBackDismiss();

        void scanPaySuccess(PayResultEntity qrcodeEntity);

        void paySuccess(PayResultEntity payResultEntity);
    }
}
