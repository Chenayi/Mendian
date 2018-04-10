package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.AccountEntity;
import com.yaoxiaoer.mendian.mvp.entity.CashierEntity;

import java.util.List;

/**
 * Created by Chenwy on 2018/2/8.
 */

public interface AccountContract {
    interface View extends IView {
        void showAccounts(AccountEntity accountEntity);

        void showMoreAccounts(List<AccountEntity.Account> accounts);

        void showCashier(List<CashierEntity.NickNameList> cashiers);
    }
}
