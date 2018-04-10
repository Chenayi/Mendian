package com.yaoxiaoer.mendian.mvp.contract;

import com.yaoxiaoer.mendian.base.IView;
import com.yaoxiaoer.mendian.mvp.entity.HomeEntity;

/**
 * Created by Chenwy on 2018/2/5.
 */

public interface HomeContract {
    interface View extends IView{
        void showHomeDatas(HomeEntity homeEntity);
    }
}
