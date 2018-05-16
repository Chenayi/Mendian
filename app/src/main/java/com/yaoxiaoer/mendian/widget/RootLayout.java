package com.yaoxiaoer.mendian.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoxiaoer.mendian.R;
import com.yatoooon.screenadaptation.ScreenAdapterTools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chenwy on 2018/2/2.
 */

public class RootLayout extends LinearLayout {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private Context mContext;
    private final View mTitleBarView;
    /**
     * 标题栏颜色
     */
    private int mTitleBarColor;

    /**
     * 标题文字
     */
    private String mTitleBarTitle;

    /**
     * 标题文字颜色
     */
    private int mTitleBarTitleColor;

    /**
     * 标题栏高度
     */
    private float mTitleBarHeight;

    /**
     * 标题栏左按钮
     */
    private int mTitleBarLeftIcon;

    /**
     * 标题栏右按钮
     */
    private int mTitleBarRightIcon;

    /**
     * 标题栏右文本
     */
    private String mTitleBarRightText;

    /**
     * 标题栏右文本颜色
     */
    private int mTitleBarRightTextColor;

    /**
     * 是否显示右文本,默认不显示
     */
    private boolean isShowRightText;

    /**
     * 是否显示右图标,默认不显示
     */
    private boolean isShowRightIcon;

    /**
     * 是否显示左边图标，默认显示
     */
    private boolean isShowLeftIcon;


    public RootLayout(Context context) {
        this(context, null);
    }

    public RootLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RootLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.RootLayout);
        mTitleBarColor = t.getColor(R.styleable.RootLayout_titleBarColor, ContextCompat.getColor(context, R.color.colorPrimary));
        mTitleBarTitle = t.getString(R.styleable.RootLayout_titleBarTitle);
        mTitleBarTitleColor = t.getColor(R.styleable.RootLayout_titleBarTitleColor, ContextCompat.getColor(context, R.color.white));
        mTitleBarHeight = t.getDimension(R.styleable.RootLayout_titleBarHeight, getResources().getDimension(R.dimen.titleBarHeight));
        mTitleBarLeftIcon = t.getResourceId(R.styleable.RootLayout_titleBarLeftIcon, R.mipmap.arrow_white);
        mTitleBarRightIcon = t.getResourceId(R.styleable.RootLayout_titleBarRightIcon, 0);
        mTitleBarRightText = t.getString(R.styleable.RootLayout_titleBarRightText);
        mTitleBarRightTextColor = t.getColor(R.styleable.RootLayout_titleBarRightTextColor, ContextCompat.getColor(context, R.color.white));
        isShowRightText = t.getBoolean(R.styleable.RootLayout_isShowRightText, false);
        isShowRightIcon = t.getBoolean(R.styleable.RootLayout_isShowRightIcon, false);
        isShowLeftIcon = t.getBoolean(R.styleable.RootLayout_isShowLeftIcon, true);
        t.recycle();

        setClipToPadding(true);
        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(context, R.color.color_bg));

        ViewGroup.LayoutParams lpTitle = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) mTitleBarHeight);
        mTitleBarView = LayoutInflater.from(context).inflate(R.layout.base_titlebar, null, false);
        if (!isInEditMode()) {
            ScreenAdapterTools.getInstance().loadView((ViewGroup) mTitleBarView);
        }
        mTitleBarView.setBackgroundColor(mTitleBarColor);
        addView(mTitleBarView, lpTitle);

        ButterKnife.bind(this, mTitleBarView);

        init();
    }

    private void init() {
        if (!TextUtils.isEmpty(mTitleBarTitle)) {
            tvTitle.setText(mTitleBarTitle);
        }
        ivLeft.setVisibility(isShowLeftIcon ? VISIBLE : GONE);
        ivLeft.setImageResource(mTitleBarLeftIcon);
        setOnLeftOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
            }
        });

        if (mTitleBarRightIcon != 0) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageResource(mTitleBarRightIcon);
        }

        if (!TextUtils.isEmpty(mTitleBarRightText)) {
            tvRight.setVisibility(VISIBLE);
            tvRight.setText(mTitleBarRightText);
        }
    }

    public RootLayout setTitle(String title) {
        tvTitle.setText(title);
        return this;
    }

    public RootLayout setOnLeftOnClickListener(OnClickListener l) {
        if (ivLeft != null && ivLeft.getVisibility() == VISIBLE) {
            ivLeft.setOnClickListener(l);
        }
        return this;
    }

    public RootLayout setRightIcon(int icon) {
        if (ivRight != null) {
            ivRight.setVisibility(VISIBLE);
            ivRight.setImageResource(icon);
        }
        return this;
    }

    public RootLayout setOnRightOnClickListener(OnClickListener l) {
        if (ivRight != null && ivRight.getVisibility() == VISIBLE) {
            ivRight.setOnClickListener(l);
            return this;
        }


        if (tvRight != null && tvRight.getVisibility() == VISIBLE){
            tvRight.setOnClickListener(l);
        }
        return this;
    }

    public View getTitleBarView() {
        return mTitleBarView;
    }

    public static RootLayout getInstance(Activity context) {
        return (RootLayout) ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }
}
