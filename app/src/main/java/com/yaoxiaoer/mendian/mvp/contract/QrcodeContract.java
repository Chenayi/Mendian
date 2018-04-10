package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.mvp.entity.QrcodeEntity;

/**
 * Created by Chenwy on 2018/2/9.
 */

public interface QrcodeContract {
    interface View extends IView {
        void showQrCode(QrcodeEntity qrcodeEntity);

        void paySuccess(PayResultEntity qrcodeEntity);
    }
}
