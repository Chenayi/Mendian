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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.bigkoo.pickerview.TimePickerView;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseDialog;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.mvp.entity.CashierEntity;
import com.yaoxiaoer.mendian.utils.NumberUtils;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.utils.Utils;
import com.yaoxiaoer.mendian.widget.CashierFilterView;
import com.yaoxiaoer.mendian.widget.DateSelectorView;
import com.yaoxiaoer.mendian.widget.OrderSourceView;
import com.yaoxiaoer.mendian.widget.PayTypeView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by Chenwy on 2018/2/6.
 */

public class AccountsOptionDialog extends BaseDialog {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sv_container)
    ScrollView svContainer;

    //根布局
    @BindView(R.id.root_layout)
    RelativeLayout rlRoot;

    //日期选择
    @BindView(R.id.date_selector_view)
    DateSelectorView dateSelectorView;

    //支付渠道
    @BindView(R.id.pay_type_view)
    PayTypeView payTypeView;

    //收银员
    @BindView(R.id.cashierFilterView)
    CashierFilterView cashierFilterView;

    //订单来源
    @BindView(R.id.order_source_view)
    OrderSourceView orderSourceView;

    //搜索
    @BindView(R.id.btn_search)
    Button btnSearch;

    //重置
    @BindView(R.id.btn_reset)
    Button btnReset;


    private Window mWindow;

    /**
     * 日期选择哪一个
     */
    private int mChooseWhat;

    /**
     * 开始时间
     */
    private String mStartDay;
    /**
     * 结束时间
     */
    private String mEndDay;
    /**
     * 订单来源
     */
    private String mOrderSource;

    /**
     * 支付渠道
     */
    private String mPayType;

    /**
     * 收银员
     */
    private List<CashierEntity.NickNameList> mCashiers;
    /**
     * 选择的收银员
     */
    private CashierEntity.NickNameList mChooseCashier;

    public static AccountsOptionDialog newInstance(int dayWhat, String startDay, String endDay
            , String payType, String orderSource
            , List<CashierEntity.NickNameList> cashiers, CashierEntity.NickNameList chooseCashier) {
        Bundle args = new Bundle();
        args.putInt("dayWhat", dayWhat);
        args.putString("startDay", startDay);
        args.putString("endDay", endDay);
        args.putString("orderSource", orderSource);
        args.putString("payType", payType);
        args.putSerializable("cashiers", (Serializable) cashiers);
        args.putSerializable("chooseCashier", (Serializable) chooseCashier);
        AccountsOptionDialog fragment = new AccountsOptionDialog();
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
        return R.layout.dialog_accounts_option;
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this, getDialog());
        mImmersionBar.titleBar(toolbar)
                .navigationBarColor(R.color.colorPrimary)
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
        //下单时间
        initDay();
        //支付渠道
        initPayType();
        //收银员
        initCashiers();
        ///订单来源
        initOrderSource();
    }

    private void initBundleData() {
        Bundle arguments = getArguments();
        mChooseWhat = arguments.getInt("dayWhat");
        mStartDay = arguments.getString("startDay");
        mEndDay = arguments.getString("endDay");
        mPayType = arguments.getString("payType");
        mOrderSource = arguments.getString("orderSource");
        mCashiers = (List<CashierEntity.NickNameList>) arguments.getSerializable("cashiers");
        mChooseCashier = (CashierEntity.NickNameList) arguments.getSerializable("chooseCashier");

    }

    /**
     * 下单时间
     */
    private void initDay() {
        dateSelectorView.initDate(mChooseWhat, mStartDay, mEndDay);
        dateSelectorView.setOnDateChooseListener(new DateSelectorView.OnDateChooseListener() {
            @Override
            public void onDateChoose(int chooseDateWhat, String startDay, String endDay) {
                cashierFilterView.hide();
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
     * 收银员
     */
    private void initCashiers() {
        cashierFilterView.setDatas(mCashiers, mChooseCashier);
        cashierFilterView.setOnCashierStatusListener(new CashierFilterView.OnCashierStatusListener() {
            @Override
            public void onCashierOpen() {
                svContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        svContainer.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
                    }
                });
            }

            @Override
            public void onCashierChoose(CashierEntity.NickNameList cashier) {
                mChooseCashier = cashier;
            }
        });
    }

    /**
     * 支付渠道
     */
    private void initPayType() {
        payTypeView.setCurPayType(mPayType);
        payTypeView.setOnPayTypeChangeListener(new PayTypeView.OnPayTypeChangeListener() {
            @Override
            public void onPayTypeChange(String payType) {
                cashierFilterView.hide();
                mPayType = payType;
            }
        });
    }

    /**
     * 订单来源
     */
    private void initOrderSource() {
        orderSourceView.setCurOrderSource(mOrderSource);
        orderSourceView.setOnOrderSourceChangeListener(new OrderSourceView.OnOrderSourceChangeListener() {
            @Override
            public void oorderSourceChange(String orderSource) {
                cashierFilterView.hide();
                mOrderSource = orderSource;
            }
        });
    }

    @OnTouch({R.id.rl_container, R.id.ll_bottom})
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            cashierFilterView.hide();
        }
        return false;
    }

    @OnClick({R.id.btn_search, R.id.btn_reset, R.id.root_layout})
    public void onClick(View v) {
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
        if (onSearchListener != null) {
            onSearchListener.onSearch(mChooseWhat, mStartDay, mEndDay
                    , mPayType, mOrderSource, mChooseCashier);
        }
        dismiss();
    }

    private void reset() {
        dateSelectorView.resetDay();
        payTypeView.resetPayType();
        cashierFilterView.resetChooseCashier();
        orderSourceView.resetOrderSource();
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

    private OnSearchListener onSearchListener;

    public AccountsOptionDialog setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
        return this;
    }

    public interface OnSearchListener {
        void onSearch(int dayWhat, String startDay, String endDate, String payType
                , String source, CashierEntity.NickNameList cashier);
    }
}
