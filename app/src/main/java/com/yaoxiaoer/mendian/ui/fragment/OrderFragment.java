package com.yaoxiaoer.mendian.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.adapter.CommomViewPagerAdapter;
import com.yaoxiaoer.mendian.base.BaseFragment;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.event.OrderStatusChangeEvent;
import com.yaoxiaoer.mendian.ui.dialog.OrderOptionDialog;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.utils.Utils;
import com.yaoxiaoer.mendian.widget.RootLayout;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单（父层tab）
 * Created by Chenwy on 2018/1/30.
 */

public class OrderFragment extends BaseFragment {
    @BindView(R.id.tl)
    SlidingTabLayout stl;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.root_layout)
    RootLayout rootLayout;

    private CommomViewPagerAdapter viewPagerAdapter;
    private boolean isOrderStatusChange = false;

    /**
     * 开始时间
     */
    private String mStartDay;
    /**
     * 结束时间
     */
    private String mEndDay;
    /**
     * 订单来源 0快速支付(门店自有) 1微商城
     */
    private String mOrderSource = "1";
    /**
     * 订单号
     */
    private String mOrderCode;
    /**
     * ]
     * 收货人
     */
    private String mBuyMember;

    /**
     * 选择哪项日期
     */
    private int mDayWhat = Order.TODAY;

    private String[] mTitles = new String[]{"全部", "未处理", "已完成", "已取消", "退款"};
    private List<Fragment> fragments;

    {
        fragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            fragments.add(OrderChildFragment.newInstance(i));
        }
    }

    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_order;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected void initData() {
        viewPagerAdapter = new CommomViewPagerAdapter(getChildFragmentManager(), fragments);
        vp.setAdapter(viewPagerAdapter);
        vp.setOffscreenPageLimit(mTitles.length - 1);
        stl.setTabWidth(SizeUtils.px2dp(ScreenUtils.getScreenWidth() / mTitles.length));
        stl.setViewPager(vp, mTitles);
        stl.setSnapOnTabClick(true);
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //订单
                ((OrderChildFragment) viewPagerAdapter.getItem(position)).refreshOrder();
            }
        });
        rootLayout.setOnRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDialog();
            }
        });
    }

    public void resetOrder() {
        if (isAcquired) {
            resetOption();
            for (Fragment fragment : viewPagerAdapter.getFragments()) {
                ((OrderChildFragment) fragment).resetOption();
            }
            if (vp.getCurrentItem() != 0) {
                vp.setCurrentItem(0, false);
            } else {
                int currentItem = vp.getCurrentItem();
                ((OrderChildFragment) viewPagerAdapter.getItem(currentItem)).refreshOrder();
            }
        }
    }

    private void showOptionDialog() {
        OrderOptionDialog.newInstance(mDayWhat, mStartDay, mEndDay, mOrderSource, mOrderCode, mBuyMember)
                .setOnSearchListener(new OrderOptionDialog.OnSearchListener() {
                    @Override
                    public void onSearch(int dayWhat, String startDay, String endDate,
                                         String source, String orderNum, String buyMember) {
                        mDayWhat = dayWhat;
                        mStartDay = startDay;
                        mEndDay = endDate;
                        mOrderSource = source;
                        mOrderCode = orderNum;
                        mBuyMember = buyMember;
                        List<Fragment> fragments = viewPagerAdapter.getFragments();
                        int currentItem = vp.getCurrentItem();
                        for (int i = 0; i < fragments.size(); i++) {
                            OrderChildFragment orderChildFragment = (OrderChildFragment) fragments.get(i);
                            orderChildFragment.setOptions(mStartDay, mEndDay,
                                    mOrderSource, mOrderCode, mBuyMember);
                            if (i == currentItem) {
                                orderChildFragment.refreshOrder();
                            }
                        }
                    }
                })
                .show(getChildFragmentManager());
    }

    private void resetOption() {
        mDayWhat = Order.TODAY;
        String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        mStartDay = Utils.getStartDateTime(nowString);
        mEndDay = Utils.getEndDateTime(nowString);
        mOrderSource = "1";
        mOrderCode = null;
        mBuyMember = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isOrderStatusChange) {
            int currentItem = vp.getCurrentItem();
            if (viewPagerAdapter != null) {
                ((OrderChildFragment) viewPagerAdapter.getItem(currentItem)).refreshOrder(false);
            }
            isOrderStatusChange = false;
        }
    }

    @Subscribe
    public void orderStatusChange(OrderStatusChangeEvent orderStatusChangeEvent) {
        isOrderStatusChange = true;
    }

    @Override
    protected boolean isLoadEvenetBus() {
        return true;
    }
}
