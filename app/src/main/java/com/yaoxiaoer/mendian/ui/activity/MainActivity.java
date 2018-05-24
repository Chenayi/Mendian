package com.yaoxiaoer.mendian.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.baidu.tts.client.SpeechSynthesizer;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.orhanobut.logger.Logger;
import com.yaoxiaoer.mendian.BuildConfig;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.adapter.CommomViewPagerAdapter;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerMainComponent;
import com.yaoxiaoer.mendian.di.module.MainModule;
import com.yaoxiaoer.mendian.event.AccountsFreeingEvent;
import com.yaoxiaoer.mendian.event.BackHomeEvent;
import com.yaoxiaoer.mendian.event.GatheringEvent;
import com.yaoxiaoer.mendian.event.PushOrderEvent;
import com.yaoxiaoer.mendian.mvp.contract.MainContract;
import com.yaoxiaoer.mendian.mvp.entity.ApkInfoEntity;
import com.yaoxiaoer.mendian.mvp.presenter.MainPresenter;
import com.yaoxiaoer.mendian.ui.dialog.TipsDialog;
import com.yaoxiaoer.mendian.ui.dialog.UpgradeDialog;
import com.yaoxiaoer.mendian.ui.fragment.AccountsFragment;
import com.yaoxiaoer.mendian.ui.fragment.GatheringFragment;
import com.yaoxiaoer.mendian.ui.fragment.HomeFragment;
import com.yaoxiaoer.mendian.ui.fragment.OrderFragment;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.widget.NoScrollViewPager;
import com.yinglan.keyboard.HideUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import q.rorbin.badgeview.QBadgeView;

/**
 * 首页 Chenwy
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.bnve)
    BottomNavigationViewEx mBnev;
    @BindView(R.id.vp_main)
    NoScrollViewPager mVPMain;

    private CommomViewPagerAdapter mVPMainAdapter;
    private List<Fragment> mFragments;
    private QBadgeView mQBadgeView;

    private TipsDialog mAccountFreeingDialog;

    {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(OrderFragment.newInstance());
        mFragments.add(GatheringFragment.newInstance());
        mFragments.add(AccountsFragment.newInstance());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideUtil.init(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        mQBadgeView = new QBadgeView(this);
        mVPMainAdapter = new CommomViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mVPMain.setAdapter(mVPMainAdapter);
        mVPMain.setOffscreenPageLimit(mFragments.size() - 1);
        initBottomBar();
        mVPMain.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    //订单
                    case 1:
                        ((OrderFragment) mVPMainAdapter.getItem(position)).resetOrder();
                        break;
                    //收款
                    case 2:
                        ((GatheringFragment) mVPMainAdapter.getItem(position)).resetMoney();
                        break;
                    //账目
                    case 3:
                        ((AccountsFragment) mVPMainAdapter.getItem(position)).resetWithRefresh(true);
                        break;
                }
            }
        });

        boolean hasNewOrder = SPUtils.getInstance().getBoolean(C.NEW_ORDER);
        if (hasNewOrder) {
            addBadgeAt(1, -1);
        }

        mPresenter.checkUpgrade();
    }

    private void initBottomBar() {
        mBnev.enableAnimation(true);
        mBnev.enableItemShiftingMode(false);
        mBnev.enableShiftingMode(false);
        mBnev.setTextSize(10f);
        mBnev.setupWithViewPager(mVPMain, false);
    }

    @Override
    protected boolean isLoadEventBus() {
        return true;
    }

    /**
     * 返回首页
     *
     * @param backHomeEvent
     */
    @Subscribe
    public void backHome(BackHomeEvent backHomeEvent) {
        mVPMain.setCurrentItem(0, false);
    }

    /**
     * 有新订单
     *
     * @param pushEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverPushNewOrderEvent(PushOrderEvent pushEvent) {
        //震动提醒
        mPresenter.vibrator();
        //语音播报
        mPresenter.playSpeech("您有新的订单");
        SPUtils.getInstance().put(C.NEW_ORDER, true);
        addBadgeAt(1, -1);
    }

    /**
     * 微商城退款
     */
    public void onReceiveRefund(){

    }

    /**
     * 固定二维码收款
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiverGathering(GatheringEvent gatheringEvent) {
        //语音播报
        if (gatheringEvent != null && !TextUtils.isEmpty(gatheringEvent.msg)) {
            mPresenter.playSpeech(gatheringEvent.msg);
        }
    }

    /**
     * 账户冻结
     *
     * @param accountsFreeingEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void accountsFreeing(AccountsFreeingEvent accountsFreeingEvent) {
        if (mAccountFreeingDialog != null && mAccountFreeingDialog.getDialog().isShowing()) {
            mAccountFreeingDialog.dismiss();
            mAccountFreeingDialog = TipsDialog.newInstance(null, accountsFreeingEvent.msg, true);
            mAccountFreeingDialog.setOnTipsOnClickListener(new TipsDialog.OnTipsOnClickListener() {
                @Override
                public void onSure() {
                    JPushInterface.deleteAlias(getApplicationContext(), C.STORE_ID);
                    SPUtils.getInstance().put(C.IS_LOGIN, false);
                    jumpActivity(LoginActivity.class);
                    finish();
                }

                @Override
                public void onCancel() {
                }
            })
                    .show(getSupportFragmentManager())
                    .setOutCancel(false)
                    .setCancelable(false);
        }
    }

    private void addBadgeAt(int position, int number) {
        mQBadgeView.setBadgeNumber(number)
                .setShowShadow(false)
                .setGravityOffset(24, 4, true)
                .bindTarget(mBnev.getBottomNavigationItemView(position));
    }

    public void hideBadge() {
        mQBadgeView.hide(true);
    }

    private void goBack() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(int code, String msg) {
    }

    @Override
    public void upgrade(ApkInfoEntity.Detail detail) {
        UpgradeDialog.newInstance(detail)
                .setMargin(40)
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }
}
