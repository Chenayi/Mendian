package com.yaoxiaoer.mendian.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseFragment;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerHomeComponent;
import com.yaoxiaoer.mendian.di.module.HomeModule;
import com.yaoxiaoer.mendian.mvp.contract.HomeContract;
import com.yaoxiaoer.mendian.mvp.entity.HomeEntity;
import com.yaoxiaoer.mendian.mvp.presenter.HomePresenter;
import com.yaoxiaoer.mendian.ui.activity.LoginActivity;
import com.yaoxiaoer.mendian.ui.dialog.TipsDialog;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.widget.RootLayout;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Chenwy on 2018/1/29.
 * 首页
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.root_layout)
    RootLayout rootLayout;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tv_today_moneys)
    TextView tvTodayMoneys;
    @BindView(R.id.tv_7_sumPrice)
    TextView tv7SumPrice;
    @BindView(R.id.tv_7_orderNumbers)
    TextView tv7orderNumbers;
    @BindView(R.id.tv_7_averagePrice)
    TextView tv7AveragePrice;
    @BindView(R.id.tv_30_sumPrice)
    TextView tv30SumPrice;
    @BindView(R.id.tv_30_orderNumbers)
    TextView tv30orderNumbers;
    @BindView(R.id.tv_30_averagePrice)
    TextView tv30AveragePrice;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_home;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        rootLayout
                .setOnRightOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TipsDialog.newInstance(null, "是否退出当前账户？", "是", "否")
                                .setOnTipsOnClickListener(new TipsDialog.OnTipsOnClickListener() {
                                    @Override
                                    public void onSure() {
                                        JPushInterface.deleteAlias(getContext(), C.STORE_ID);
                                        SPUtils.getInstance().put(C.IS_LOGIN, false);
                                        jumpActivity(LoginActivity.class);
                                        _mActivity.finish();
                                    }

                                    @Override
                                    public void onCancel() {
                                    }
                                })
                                .show(getChildFragmentManager());
                    }
                });
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHomeDatas(false);
            }
        });
        refreshHomeDatas(true);
    }

    public void refreshHomeDatas(boolean isShowLoading) {
        mPresenter.requestHomeDatas(isShowLoading);
    }

    @Override
    public void showLoading() {
        startLoading();
    }

    @Override
    public void hideLoading() {
        stopLoading();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onError(int code, String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showHomeDatas(HomeEntity homeEntity) {
        HomeEntity.StoreInfoBean storeInfo = homeEntity.getStoreInfo();
        if (null != storeInfo) {
            rootLayout.setTitle(storeInfo.getStoreName());
        }

        HomeEntity.OnedayBean oneday = homeEntity.getOneday();
        if (null != oneday) {
            tvTodayMoneys.setText("￥" + oneday.getSumPrice());
        }

        HomeEntity.SevendayBean sevenday = homeEntity.getSevenday();
        if (null != sevenday) {
            tv7SumPrice.setText(sevenday.getSumPrice() + "");
            tv7orderNumbers.setText(sevenday.getOrderNumbers() + "");
            tv7AveragePrice.setText(sevenday.getAveragePrice() + "");
        }

        HomeEntity.ThirtydayBean thirtyday = homeEntity.getThirtyday();
        if (null != thirtyday) {
            tv30SumPrice.setText(thirtyday.getSumPrice() + "");
            tv30orderNumbers.setText(thirtyday.getOrderNumbers() + "");
            tv30AveragePrice.setText(thirtyday.getAveragePrice() + "");
        }
    }
}
