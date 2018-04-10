package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.AccountDetailEntity;

/**
 * Created by Chenwy on 2018/2/8.
 */

public interface AccountDetailContract {
    interface View extends IView {
        void showAccountDetail(AccountDetailEntity.Detail detail);
    }
}
