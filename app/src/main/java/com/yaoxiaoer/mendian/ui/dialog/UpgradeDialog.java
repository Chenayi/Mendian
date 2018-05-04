package com.yaoxiaoer.mendian.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseDialog;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerUpgradeComponent;
import com.yaoxiaoer.mendian.di.module.UpgradeModule;
import com.yaoxiaoer.mendian.event.DownloadProgressEvent;
import com.yaoxiaoer.mendian.mvp.contract.UpgradeContract;
import com.yaoxiaoer.mendian.mvp.entity.ApkInfoEntity;
import com.yaoxiaoer.mendian.mvp.presenter.UpgradePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Chenwy on 2018/3/2.
 * 更新 dialog
 */

public class UpgradeDialog extends BaseDialog<UpgradePresenter> implements UpgradeContract.View {
    @BindView(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    @BindView(R.id.btn_upgrade)
    Button btnUpgrade;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_close)
    LinearLayout llClose;

    private ApkInfoEntity.Detail mApkInfo;
    private boolean isDownloading;

    public static UpgradeDialog newInstance(ApkInfoEntity.Detail detail) {
        Bundle args = new Bundle();
        args.putSerializable("apkInfo", detail);
        UpgradeDialog fragment = new UpgradeDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_upgrade;
    }

    @Override
    protected void initImmersionBar() {
    }

    @Override
    protected void setUpComponent(AppComponent appComponent) {
        DaggerUpgradeComponent.builder()
                .appComponent(appComponent)
                .upgradeModule(new UpgradeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        mApkInfo = (ApkInfoEntity.Detail) getArguments().getSerializable("apkInfo");
        llClose.setVisibility(mApkInfo.getIsMust().equals("0") ? View.VISIBLE : View.GONE);
        tvTitle.setText("是否升级到" + mApkInfo.getVersionName() + "版本？");
        tvSize.setText("新版本大小：" + mApkInfo.getPacketSize());
        tvContent.setText(mApkInfo.getUpgradeContent().replace("\\n", "\n"));

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (!isDownloading && mApkInfo.getIsMust().equals("0")) {
                        dismiss();
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.btn_upgrade, R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_upgrade:
                isDownloading = true;
                llClose.setVisibility(View.GONE);
                btnUpgrade.setVisibility(View.GONE);
                numberProgressBar.setVisibility(View.VISIBLE);
                mPresenter.downloadApk((mApkInfo.getDownloadUrl()), "yaoxiaoer_v" + mApkInfo.getVersionName() + ".apk");
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }


    @Override
    protected boolean isLoadEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void progress(DownloadProgressEvent downloadProgressEvent) {
        numberProgressBar.setMax(downloadProgressEvent.total);
        numberProgressBar.setProgress(downloadProgressEvent.progress);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onError(int code, String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void downLoadSuccess(File apkFile) {
        AppUtils.installApp(apkFile, "com.yaoxiaoer.mendian.provider");
        dismiss();
        mActivity.finish();
    }
}
