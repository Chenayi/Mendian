package com.yaoxiaoer.mendian.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseListFragment;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerAccountComponent;
import com.yaoxiaoer.mendian.di.module.AccountModule;
import com.yaoxiaoer.mendian.event.BackToAccountsEvent;
import com.yaoxiaoer.mendian.mvp.contract.AccountContract;
import com.yaoxiaoer.mendian.mvp.entity.AccountEntity;
import com.yaoxiaoer.mendian.mvp.entity.CashierEntity;
import com.yaoxiaoer.mendian.mvp.presenter.AccountPresenter;
import com.yaoxiaoer.mendian.ui.activity.AccountDetailActivity;
import com.yaoxiaoer.mendian.ui.dialog.AccountsOptionDialog;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.utils.Utils;
import com.yaoxiaoer.mendian.widget.RootLayout;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;

/**
 * 账目
 * Created by Chenwy on 2018/2/8.
 */

public class AccountsFragment extends BaseListFragment<AccountPresenter, AccountEntity.Account>
        implements AccountContract.View {
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_sum)
    TextView tvOrderSum;
    @BindView(R.id.root_layout)
    RootLayout mRootLayout;

    /**
     * 选择哪项日期
     */
    private int mDayWhat = Order.TODAY;

    /**
     * 开始时间
     */
    private String mStartTime;
    /**
     * 结束时间
     */
    private String mEndTime;
    /**
     * 支付渠道
     */
    private String mPayType;
    /**
     * 订单来源
     */
    private String mOrderSource;

    private List<CashierEntity.NickNameList> mCashiers;

    private CashierEntity.NickNameList mChooseCashier;

    public static AccountsFragment newInstance() {
        Bundle args = new Bundle();
        AccountsFragment fragment = new AccountsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String nowDate = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        mStartTime = Utils.getStartDateTime(nowDate);
        mEndTime = Utils.getEndDateTime(nowDate);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_accounts;
    }

    @Override
    public String getEmptyTips() {
        return "亲，暂无账目数据";
    }

    @Override
    protected void initData() {
        mRootLayout.setOnRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCashiers == null) {
                    mPresenter.reqestCashier();
                } else {
                    showAccountsOptionDialog();
                }
            }
        });
        super.initData();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerAccountComponent.builder()
                .appComponent(appComponent)
                .accountModule(new AccountModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void refreshData() {
        mPresenter.requestAccounts(pageNo, C.PAGE_SIZE, mStartTime, mEndTime
                , mChooseCashier == null ? null : mChooseCashier.u_id
                , mPayType, mOrderSource);
    }

    @Override
    protected int itemLayout() {
        return R.layout.item_accounts;
    }

    private void showAccountsOptionDialog() {
        AccountsOptionDialog.newInstance(mDayWhat, mStartTime,
                mEndTime, mPayType, mOrderSource
                , mCashiers, mChooseCashier)
                .setOnSearchListener(new AccountsOptionDialog.OnSearchListener() {
                    @Override
                    public void onSearch(int dayWhat, String startDay, String endDate
                            , String payType, String source, CashierEntity.NickNameList cashier) {
                        mDayWhat = dayWhat;
                        mStartTime = startDay;
                        mEndTime = endDate;
                        mPayType = payType;
                        mOrderSource = source;
                        mChooseCashier = cashier;

                        startLoading();
                        pageNo = 1;
                        mPresenter.requestAccounts(pageNo, C.PAGE_SIZE, mStartTime, mEndTime
                                , mChooseCashier == null ? null : mChooseCashier.u_id
                                , mPayType, mOrderSource);
                    }
                })
                .show(getChildFragmentManager());
    }


    public void resetWithRefresh(boolean isShowLoading) {
        if (isAcquired) {
            mDayWhat = Order.TODAY;
            String nowDate = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
            mStartTime = Utils.getStartDateTime(nowDate);
            mEndTime = Utils.getEndDateTime(nowDate);

            mPayType = null;
            mOrderSource = null;
            mCashiers = null;
            mChooseCashier = null;

            if (isShowLoading) {
                startLoading();
            }

            hideEmptyView();
            clearAll();
            pageNo = 1;
            mPresenter.requestAccounts(pageNo, C.PAGE_SIZE, mStartTime, mEndTime
                    , mChooseCashier == null ? null : mChooseCashier.u_id
                    , mPayType, mOrderSource);
        }
    }

    @Override
    protected void myHolder(BaseViewHolder helper, AccountEntity.Account data) {
        helper.setText(R.id.tv_order_price, "￥" + data.orderPrice)
                .setText(R.id.tv_username, data.unickname)
                .setGone(R.id.top_line, helper.getLayoutPosition() == 0)
                .setText(R.id.tv_wsc_order_code, "支付订单号  " + data.orderCode);

        if (!TextUtils.isEmpty(data.timeEndToString)) {
            helper.setText(R.id.tv_order_time, data.timeEndToString.split(" ")[1]);
        }
        //支付方式
        String payType = data.payType;
        if (payType.equals(Order.PAY_TYPE_WX)) {
            helper.setText(R.id.tv_pay_type, "微")
                    .setBackgroundColor(R.id.tv_pay_type, ContextCompat.getColor(getContext(), R.color.wx));
        } else if (payType.equals(Order.PAY_TYPE_ALIPAY)) {
            helper.setText(R.id.tv_pay_type, "支")
                    .setBackgroundColor(R.id.tv_pay_type, ContextCompat.getColor(getContext(), R.color.alipay));
        } else if (payType.equals(Order.PAY_TYPE_CASH)) {
            helper.setText(R.id.tv_pay_type, "现")
                    .setBackgroundColor(R.id.tv_pay_type, ContextCompat.getColor(getContext(), R.color.color_ff9600));
        }

        //退款状态
        int orderStatus = data.orderStatus;
        helper.setGone(R.id.tv_refund_status, orderStatus == 18);
        //退款成功
        if (orderStatus == 18) {
            helper.setText(R.id.tv_refund_status, "退款成功");
        }
    }

    @Override
    protected void onListItemClick(AccountEntity.Account data, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("orderId", data.orderId);
        jumpActivity(bundle, AccountDetailActivity.class);
    }

    @Override
    protected void onListItemChildClick(int viewId, AccountEntity.Account data, int position) {

    }

    @Override
    public void showLoading() {
        startLoading();
    }

    @Override
    public void hideLoading() {
        stopLoading();
    }

    @Override
    public void onError(int code, String msg) {
        handleError(code, msg);
    }

    @Override
    public void showAccounts(AccountEntity accountEntity) {
        String num = "0";
        String sum = "0";

        String orderNumbers = accountEntity.sum.orderNumbers;
        if (!TextUtils.isEmpty(orderNumbers)) {
            num = orderNumbers;
        }

        String sumPrice = accountEntity.sum.sumPrice;
        if (!TextUtils.isEmpty(sumPrice)) {
            sum = sumPrice;
        }

        tvOrderNum.setText("合计" + num + "笔");
        tvOrderSum.setText("总计" + sum + "元");
        List<AccountEntity.Account> accounts = accountEntity.list;
        finishRefresh(accounts);
    }


    @Override
    public void showMoreAccounts(List<AccountEntity.Account> accounts) {
        finishLoadMore(accounts);
    }

    @Override
    public void showCashier(List<CashierEntity.NickNameList> cashiers) {
        cashiers.add(0, null);
        mCashiers = cashiers;
        showAccountsOptionDialog();
    }

    @Override
    protected boolean isLoadEvenetBus() {
        return true;
    }

    @Subscribe
    public void backToAccounts(BackToAccountsEvent backToAccountsEvent) {
        resetWithRefresh(false);
    }
}
