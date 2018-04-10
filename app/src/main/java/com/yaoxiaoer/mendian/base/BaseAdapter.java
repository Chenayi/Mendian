package com.yaoxiaoer.mendian.base;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import java.util.List;

/**
 * Created by Chenwy on 2018/3/14.
 */

public abstract class BaseAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    public BaseAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = super.getItemView(layoutResId, parent);
        ScreenAdapterTools.getInstance().loadView((ViewGroup) view);
        return view;
    }
}
