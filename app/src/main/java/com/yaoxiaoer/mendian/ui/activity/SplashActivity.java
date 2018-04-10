package com.yaoxiaoer.mendian.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerSplashComponent;
import com.yaoxiaoer.mendian.di.module.SplashModule;
import com.yaoxiaoer.mendian.mvp.contract.SplashContract;
import com.yaoxiaoer.mendian.mvp.presenter.SplashPresenter;
import com.yaoxiaoer.mendian.ui.dialog.TipsDialog;
import com.yaoxiaoer.mendian.widget.SplashScanView;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 闪屏页
 * Created by Chenwy on 2018/1/31.
 */
@RuntimePermissions
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {
    @BindView(R.id.splash_scan_view)
    SplashScanView splashScanView;

    private final int REQUEST_SETTING = 123;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerSplashComponent.builder()
                .appComponent(appComponent)
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        SplashActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(this);
    }

    @Override
    protected void initImmersinBar() {
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void requestPermission() {
        mPresenter.checkLogin();
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showRationale(final PermissionRequest request) {
        TipsDialog.newInstance(null, "需要存储权限")
                .setOnTipsOnClickListener(new TipsDialog.OnTipsOnClickListener() {
                    @Override
                    public void onSure() {
                        request.proceed();
                    }

                    @Override
                    public void onCancel() {
                        request.cancel();
                    }
                })
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showDenied() {
        SplashActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(this);
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showNeverAsk() {
        TipsDialog.newInstance(null, "请到设置中打开存储权限")
                .setOnTipsOnClickListener(new TipsDialog.OnTipsOnClickListener() {
                    @Override
                    public void onSure() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(intent, REQUEST_SETTING);
                    }

                    @Override
                    public void onCancel() {
                    }
                })
                .setOutCancel(false)
                .show(getSupportFragmentManager());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SETTING) {
            SplashActivityPermissionsDispatcher.requestPermissionWithPermissionCheck(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onBackPressedSupport() {
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
    public void startScanAnim(final boolean isLogin) {
        splashScanView.startScanAnim(new SplashScanView.OnAnimEndListener() {
            @Override
            public void onAnimEnd() {
                if (isLogin) {
                    jumpActivity(MainActivity.class);
                } else {
                    jumpActivity(LoginActivity.class);
                }
                finish();
            }
        });
    }
}
