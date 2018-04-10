package com.yaoxiaoer.mendian.base;

import android.view.View;

import com.yaoxiaoer.mendian.widget.RootLayout;

/**
 * Created by Chenwy on 2018/3/15.
 */

public abstract class BaseTitleBarListActivity<P extends IPresenter, D> extends BaseListActivity<P,D> {
    private RootLayout rootLayout;
    @Override
    protected void initDatas() {
        rootLayout = RootLayout.getInstance(this);
        rootLayout.setTitle(getBarTitle());
        super.initDatas();
    }

    protected void setOnRightClickListener(int rightIcon, View.OnClickListener l) {
        rootLayout.setRightIcon(rightIcon)
                .setOnRightOnClickListener(l);
    }
}
