package com.yaoxiaoer.mendian.ui.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseDialog;
import com.yaoxiaoer.mendian.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Chenwy on 2017/9/14.
 */

public class TipsDialog extends BaseDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.line)
    View line;

    private String title;
    private String content;
    private String leftText;
    private String rightText;
    private int leftTextColor;
    private boolean isHideRightText = false;
    private OnTipsOnClickListener onTipsOnClickListener;

    public static TipsDialog newInstance(String title, String content, boolean isHideRightText) {
        TipsDialog tipsDialog = new TipsDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        bundle.putBoolean("isHideRightText", isHideRightText);
        tipsDialog.setArguments(bundle);
        tipsDialog.setMargin(52);
        return tipsDialog;
    }

    public static TipsDialog newInstance(String title, String content) {
        TipsDialog tipsDialog = new TipsDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        tipsDialog.setArguments(bundle);
        tipsDialog.setMargin(52);
        return tipsDialog;
    }

    public static TipsDialog newInstance(String title, String content, int leftTextColor, String leftText, String rightText) {
        TipsDialog tipsDialog = new TipsDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        bundle.putInt("leftTextColor", leftTextColor);
        bundle.putString("leftText", leftText);
        bundle.putString("rightText", rightText);
        tipsDialog.setArguments(bundle);
        tipsDialog.setMargin(52);
        return tipsDialog;
    }

    public static TipsDialog newInstance(String title, String content, String leftText, String rightText) {
        TipsDialog tipsDialog = new TipsDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("content", content);
        bundle.putString("leftText", leftText);
        bundle.putString("rightText", rightText);
        tipsDialog.setArguments(bundle);
        tipsDialog.setMargin(52);
        return tipsDialog;
    }

    public TipsDialog setOnTipsOnClickListener(OnTipsOnClickListener onTipsOnClickListener) {
        this.onTipsOnClickListener = onTipsOnClickListener;
        return this;
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_tips;
    }

    @Override
    protected void initImmersionBar() {
    }

    @Override
    protected void setUpComponent(AppComponent appComponent) {
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        if (null != arguments) {
            title = arguments.getString("title");
            leftText = arguments.getString("leftText");
            rightText = arguments.getString("rightText");
            leftTextColor = arguments.getInt("leftTextColor");
            content = arguments.getString("content");
            isHideRightText = arguments.getBoolean("isHideRightText");
        }

        if (leftTextColor != 0) {
            tvLeft.setTextColor(leftTextColor);
        }

        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(content)) {
            tvContent.setText(content);
        }

        if (!TextUtils.isEmpty(leftText)) {
            tvLeft.setText(leftText);
        }

        if (!TextUtils.isEmpty(rightText)) {
            tvRight.setText(rightText);
        }

        if (isHideRightText) {
            tvRight.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_left, R.id.tv_right})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                if (onTipsOnClickListener != null) {
                    onTipsOnClickListener.onSure();
                }
                dismiss();
                break;
            case R.id.tv_right:
                if (onTipsOnClickListener != null) {
                    onTipsOnClickListener.onCancel();
                }
                dismiss();
                break;
        }
    }

    public interface OnTipsOnClickListener {
        void onSure();

        void onCancel();
    }
}
