package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.CacheUsers;
import com.yaoxiaoer.mendian.mvp.entity.User;

import java.util.List;

/**
 * Created by Chenwy on 2018/1/31.
 */

public interface LoginContract {
    interface View extends IView {
        void showCacheUsers(List<User> users);

        void loginSuccess();
    }
}
