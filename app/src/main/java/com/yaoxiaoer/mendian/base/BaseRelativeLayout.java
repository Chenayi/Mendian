package com.yaoxiaoer.mendian.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Chenwy on 2018/3/14.
 */

public abstract class BaseRelativeLayout extends RelativeLayout {
    private final Unbinder bind;
    protected Context mContext;

    public BaseRelativeLayout(Context context) {
        this(context, null);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(), this, true);
        if (!isInEditMode()){
            ScreenAdapterTools.getInstance().loadView((ViewGroup) view);
        }
        bind = ButterKnife.bind(view);
        init(attrs, defStyleAttr);
    }

    protected abstract int getLayoutId();

    protected abstract void init(AttributeSet attrs, int defStyleAttr);

    public void destory() {
        if (bind != null) {
            bind.unbind();
        }
    }
}
