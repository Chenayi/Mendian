package com.yaoxiaoer.mendian.ui.dialog;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseDialog;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.utils.NumberUtils;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.utils.Utils;
import com.yaoxiaoer.mendian.widget.DateSelectorView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by Chenwy on 2018/2/6.
 */

public class OrderOptionDialog extends BaseDialog {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.date_selector_view)
    DateSelectorView dateSelectorView;
    @BindView(R.id.et_order_num)
    EditText etOrderNum;
    @BindView(R.id.et_buy_member)
    EditText etBuyMember;
    @BindView(R.id.root_layout)
    RelativeLayout rlRoot;

    private Window mWindow;

    /**
     * 日期选择哪一个
     */
    private int mChooseWhat = Order.TODAY;

    /**
     * 开始时间
     */
    private String mStartDay;
    /**
     * 结束时间
     */
    private String mEndDay;
    /**
     * 订单来源 0快速支付 1微商城
     */
    private String mOrderSource = Order.SOURCE_WSC;
    /**
     * 订单号
     */
    private String mOrderCode;
    /**
     * ]
     * 收货人
     */
    private String mBuyMember;

    public static OrderOptionDialog newInstance(int dayWhat, String startDay, String endDay,
                                                String orderSource, String orderCode, String buyMember) {
        Bundle args = new Bundle();
        args.putInt("dayWhat", dayWhat);
        args.putString("startDay", startDay);
        args.putString("endDay", endDay);
        args.putString("orderSource", orderSource);
        args.putString("orderCode", orderCode);
        args.putString("buyMember", buyMember);
        OrderOptionDialog fragment = new OrderOptionDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow = getDialog().getWindow();
        mWindow.setGravity(Gravity.TOP | Gravity.END);
        mWindow.setWindowAnimations(R.style.RightDialog);
        mWindow.setLayout(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_order_option;
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this, getDialog());
        mImmersionBar.titleBar(toolbar)
                .keyboardEnable(true)
                .init();
    }

    @Override
    protected void setUpComponent(AppComponent appComponent) {
    }

    @Override
    protected void initData() {
        rlRoot.setPadding((int) (ScreenUtils.getScreenWidth() * 0.36f), 0, 0, 0);
        //bundle 数据
        initBundleData();
        //日期
        initDay();
        //订单号
        initOederCode();
        //收货人
        initBuyMember();
    }

    /**
     * 初始化bundle 数据
     */
    private void initBundleData() {
        Bundle arguments = getArguments();
        mChooseWhat = arguments.getInt("dayWhat");
        mStartDay = arguments.getString("startDay");
        mEndDay = arguments.getString("endDay");
        mOrderSource = arguments.getString("orderSource");
        mOrderCode = arguments.getString("orderCode");
        mBuyMember = arguments.getString("buyMember");
    }

    /**
     * 日期初始化
     */
    private void initDay() {
        dateSelectorView.initDate(mChooseWhat, mStartDay, mEndDay);
        dateSelectorView.setOnDateChooseListener(new DateSelectorView.OnDateChooseListener() {
            @Override
            public void onDateChoose(int chooseDateWhat, String startDay, String endDay) {
                hideKeyboard();
                mChooseWhat = chooseDateWhat;
                mStartDay = startDay;
                mEndDay = endDay;
                if (mChooseWhat == Order.START_DATE) {
                    showDatePicker(mStartDay);
                } else if (mChooseWhat == Order.END_DATE) {
                    showDatePicker(mEndDay);
                }
            }
        });
    }

    /**
     * 订单号初始化
     */
    private void initOederCode() {
        etOrderNum.setText(mOrderCode == null ? "" : mOrderCode);
        if (!TextUtils.isEmpty(mOrderCode)) {
            etOrderNum.setSelection(mOrderCode.length());
        }
    }

    /**
     * 收货人初始化
     */
    private void initBuyMember() {
        etBuyMember.setText(mBuyMember == null ? "" : mBuyMember);
        if (!TextUtils.isEmpty(mBuyMember)) {
            etBuyMember.setSelection(mBuyMember.length());
        }
    }

    /**
     * 显示日期选择器
     *
     * @param date
     */
    private void showDatePicker(String date) {
        Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(date)) {
            if (date.contains(" ")) {
                date = date.split(" ")[0];
            }
            if (date.contains("-")) {
                String[] split = date.split("-");
                String year = split[0];
                String month = split[1];
                String day = split[2];
                calendar.set(NumberUtils.parseInt(year), NumberUtils.parseInt(month) - 1, NumberUtils.parseInt(day));
            }
        }
        TimePickerView timePickerView = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mChooseWhat == Order.START_DATE) {
                    String startDay = TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd"));
                    mStartDay = Utils.getStartDateTime(startDay);
                    dateSelectorView.setStartDay(startDay);
                } else if (mChooseWhat == Order.END_DATE) {
                    String endDay = TimeUtils.date2String(date, new SimpleDateFormat("yyyy-MM-dd"));
                    mEndDay = Utils.getEndDateTime(endDay);
                    dateSelectorView.setEndDay(endDay);
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setDecorView((ViewGroup) rlRoot.getParent())
                .setDate(calendar)
                .setSubmitColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))//确定按钮文字颜色
                .setCancelColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))//取消按钮文字颜色
                .setLineSpacingMultiplier(1.8f)
                .setContentSize(20)
                .build();
        timePickerView.show();
    }

    @OnTouch({R.id.rl_container,R.id.ll_bottom})
    public boolean onTouch(View v,MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideKeyboard();
        }
        return false;
    }

    @OnClick({R.id.btn_search, R.id.btn_reset, R.id.root_layout})
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.btn_search:
                search();
                break;
            case R.id.btn_reset:
                reset();
                break;
            case R.id.root_layout:
                dismiss();
                break;
        }
    }

    private void search() {
        //订单来源
        mOrderSource = Order.SOURCE_WSC;

        //订单号
        mOrderCode = etOrderNum.getText().toString().trim();

        //收货人
        mBuyMember = etBuyMember.getText().toString().trim();

        if (onSearchListener != null) {
            onSearchListener.onSearch(mChooseWhat, mStartDay, mEndDay, mOrderSource, mOrderCode, mBuyMember);
        }

        dismiss();
    }

    /**
     * 重置
     */
    private void reset() {
        dateSelectorView.resetDay();
        mOrderSource = Order.SOURCE_WSC;
        mOrderCode = null;
        etOrderNum.setText("");
        mBuyMember = null;
        etBuyMember.setText("");
    }

    private OnSearchListener onSearchListener;

    public OrderOptionDialog setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
        return this;
    }

    public interface OnSearchListener {
        void onSearch(int dayWhat, String startDay, String endDate, String source, String orderNum, String buyMember);
    }
}