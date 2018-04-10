package com.yaoxiaoer.mendian.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseRelativeLayout;
import com.yaoxiaoer.mendian.utils.Order;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Chenwy on 2018/3/1.
 */

public class OrderSourceView extends BaseRelativeLayout {
    @BindView(R.id.tv_source_all)
    TextView tvSourceAll;
    @BindView(R.id.tv_source_wsc)
    TextView tvSourceWsc;
    @BindView(R.id.tv_source_store)
    TextView tvSourceStore;
    private String mOrderSource;

    public OrderSourceView(Context context) {
        super(context);
    }

    public OrderSourceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderSourceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.order_source_view;
    }

    @Override
    protected void init(AttributeSet attrs, int defStyleAttr) {
    }

    @OnClick({R.id.tv_source_all, R.id.tv_source_wsc, R.id.tv_source_store})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_source_all:
                setDefaultSouce();
                tvSourceAll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvSourceAll.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mOrderSource = null;
                break;
            case R.id.tv_source_wsc:
                setDefaultSouce();
                tvSourceWsc.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvSourceWsc.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mOrderSource = Order.SOURCE_WSC;
                break;
            case R.id.tv_source_store:
                setDefaultSouce();
                tvSourceStore.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvSourceStore.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mOrderSource = Order.SOURCE_STORE;
                break;
        }

        if (onOrderSourceChangeListener != null) {
            onOrderSourceChangeListener.oorderSourceChange(mOrderSource);
        }
    }

    public void setCurOrderSource(String orderSource) {
        mOrderSource = orderSource;
        if (mOrderSource == null) {
            setDefaultSouce();
            tvSourceAll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            tvSourceAll.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        } else if (mOrderSource.equals(Order.SOURCE_WSC)) {
            setDefaultSouce();
            tvSourceWsc.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            tvSourceWsc.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        } else if (mOrderSource.equals(Order.SOURCE_STORE)) {
            setDefaultSouce();
            tvSourceStore.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            tvSourceStore.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        }
    }

    public void resetOrderSource() {
        setDefaultSouce();
        tvSourceAll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        tvSourceAll.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        mOrderSource = null;

        if (onOrderSourceChangeListener != null) {
            onOrderSourceChangeListener.oorderSourceChange(mOrderSource);
        }
    }

    private void setDefaultSouce() {
        tvSourceAll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvSourceAll.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tvSourceWsc.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvSourceWsc.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tvSourceStore.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvSourceStore.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));
    }

    private OnOrderSourceChangeListener onOrderSourceChangeListener;

    public void setOnOrderSourceChangeListener(OnOrderSourceChangeListener onOrderSourceChangeListener) {
        this.onOrderSourceChangeListener = onOrderSourceChangeListener;
    }

    public interface OnOrderSourceChangeListener {
        void oorderSourceChange(String orderSource);
    }
}
