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

public class PayTypeView extends BaseRelativeLayout {
    @BindView(R.id.tv_pay_type_all)
    TextView tvPayTypeAll;
    @BindView(R.id.tv_pay_type_wx)
    TextView tvPayTypeWx;
    @BindView(R.id.tv_pay_type_ali)
    TextView tvPayTypeAli;

    /**
     * 支付渠道
     */
    private String mPayType;

    public PayTypeView(Context context) {
        super(context);
    }

    public PayTypeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PayTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.pay_type_view;
    }

    @Override
    protected void init(AttributeSet attrs, int defStyleAttr) {
    }

    @OnClick({R.id.tv_pay_type_all, R.id.tv_pay_type_wx, R.id.tv_pay_type_ali})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pay_type_all:
                setDefaultPayType();
                tvPayTypeAll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvPayTypeAll.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mPayType = null;
                break;
            case R.id.tv_pay_type_wx:
                setDefaultPayType();
                tvPayTypeWx.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvPayTypeWx.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mPayType = Order.PAY_TYPE_WX;
                break;
            case R.id.tv_pay_type_ali:
                setDefaultPayType();
                tvPayTypeAli.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvPayTypeAli.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                mPayType = Order.PAY_TYPE_ALIPAY;
                break;
        }

        if (onPayTypeChangeListener != null) {
            onPayTypeChangeListener.onPayTypeChange(mPayType);
        }
    }

    public void setCurPayType(String payType) {
        if (payType == null) {
            setDefaultPayType();
            tvPayTypeAll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            tvPayTypeAll.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        } else if (payType.equals(Order.PAY_TYPE_WX)) {
            setDefaultPayType();
            tvPayTypeWx.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            tvPayTypeWx.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        } else if (payType.equals(Order.PAY_TYPE_ALIPAY)) {
            setDefaultPayType();
            tvPayTypeAli.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            tvPayTypeAli.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        }

        mPayType = payType;
    }

    private void setDefaultPayType() {
        tvPayTypeAll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvPayTypeAll.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tvPayTypeWx.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvPayTypeWx.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tvPayTypeAli.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvPayTypeAli.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));
    }

    private OnPayTypeChangeListener onPayTypeChangeListener;

    public void setOnPayTypeChangeListener(OnPayTypeChangeListener onPayTypeChangeListener) {
        this.onPayTypeChangeListener = onPayTypeChangeListener;
    }

    public void resetPayType() {
        setDefaultPayType();
        tvPayTypeAll.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        tvPayTypeAll.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        mPayType = null;
        if (onPayTypeChangeListener != null) {
            onPayTypeChangeListener.onPayTypeChange(mPayType);
        }
    }

    public interface OnPayTypeChangeListener {
        void onPayTypeChange(String payType);
    }
}
