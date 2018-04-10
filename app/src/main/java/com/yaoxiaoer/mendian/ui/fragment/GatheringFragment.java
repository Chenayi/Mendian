package com.yaoxiaoer.mendian.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseFragment;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerGatheringComponent;
import com.yaoxiaoer.mendian.di.module.GatheringModule;
import com.yaoxiaoer.mendian.mvp.contract.GatheringContract;
import com.yaoxiaoer.mendian.mvp.entity.PayResultEntity;
import com.yaoxiaoer.mendian.mvp.presenter.GatheringPresenter;
import com.yaoxiaoer.mendian.ui.activity.PaySuccessActivity;
import com.yaoxiaoer.mendian.ui.activity.QrcodeActivity;
import com.yaoxiaoer.mendian.ui.activity.ScanActivity;
import com.yaoxiaoer.mendian.ui.dialog.TipsDialog;
import com.yaoxiaoer.mendian.utils.NumberUtils;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.widget.CaculatorView;
import com.yinglan.keyboard.HideUtil;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 收款
 * Created by Chenwy on 2018/1/30.
 */
@RuntimePermissions
public class GatheringFragment extends BaseFragment<GatheringPresenter> implements GatheringContract.View {
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.rg_channel)
    RadioGroup rgChannel;
    @BindView(R.id.rg_scan_way)
    RadioGroup rgScanWay;
    @BindView(R.id.switch_compat)
    SwitchCompat switchCompat;
    @BindView(R.id.caculator)
    CaculatorView caculatorView;
    @BindView(R.id.sv_container)
    ScrollView svContainer;

    private final int REQUEST_SCAN_CODE = 0x221;
    private final int REQUEST_CAMERA = 123;

    /**
     * 默认支付方式：支付宝
     */
    private String payType = Order.PAY_ALIPAY;

    public static GatheringFragment newInstance() {
        GatheringFragment fragment = new GatheringFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_gathering;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HideUtil.init(_mActivity);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerGatheringComponent.builder()
                .appComponent(appComponent)
                .gatheringModule(new GatheringModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData() {
        etMoney.requestFocus();


        initCaculatorView();

        rgChannel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hideKeyboard();
                if (checkedId == R.id.way_zfb) {
                    payType = Order.PAY_ALIPAY;
                    ((RadioButton) rgScanWay.getChildAt(0)).setText("支付宝二维码");
                } else if (checkedId == R.id.way_wx) {
                    payType = Order.PAY_WX;
                    ((RadioButton) rgScanWay.getChildAt(0)).setText("微信二维码");
                }
            }
        });

        rgScanWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hideKeyboard();
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hideKeyboard();
                if (isChecked) {
                    caculatorView.open();
                } else {
                    caculatorView.close();
                }
            }
        });


        KeyboardUtils.registerSoftInputChangedListener(_mActivity, new KeyboardUtils.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                if (height > 0){
                    //滚动到顶部
                    svContainer.post(new Runnable() {
                        @Override
                        public void run() {
                            svContainer.fullScroll(ScrollView.FOCUS_UP);
                        }
                    });
                }
            }
        });
    }

    private void initCaculatorView() {
        caculatorView.setOnResultCallBack(new CaculatorView.OnResultCallBack() {
            @Override
            public void onResultCallBack(String result) {
                switchCompat.setChecked(false);
                etMoney.setText(result);
                etMoney.setSelection(result.length());
            }
        });

        caculatorView.setOnVisiableStatusChangeListener(new CaculatorView.OnSwitchStatusChangeListener() {
            @Override
            public void open() {
                //滚动到底部
                svContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        svContainer.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void close() {
                //滚动到顶部
                svContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        svContainer.fullScroll(ScrollView.FOCUS_UP);
                    }
                });
            }
        });
    }

    public void resetMoney() {
        if (isAcquired) {
            etMoney.setText("");
            etMoney.requestFocus();
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        jumpActivityForResult(REQUEST_SCAN_CODE, ScanActivity.class);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        TipsDialog.newInstance(null, "需要相机权限")
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
                .show(getChildFragmentManager());
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        GatheringFragmentPermissionsDispatcher.showCameraWithPermissionCheck(this);
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        TipsDialog.newInstance(null, "请到设置中打开相机权限")
                .setOnTipsOnClickListener(new TipsDialog.OnTipsOnClickListener() {
                    @Override
                    public void onSure() {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getContext().getPackageName(), null));
                        startActivityForResult(intent, REQUEST_CAMERA);
                    }

                    @Override
                    public void onCancel() {
                    }
                })
                .setOutCancel(false)
                .show(getChildFragmentManager());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GatheringFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @OnClick({R.id.btn_sure})
    public void onClick(View v) {
        hideKeyboard();
        switch (v.getId()) {
            case R.id.btn_sure:
                String money = etMoney.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    ToastUtils.showShort("请输入收款金额");
                    return;
                }
                if (NumberUtils.parseFloat(money) <= 0) {
                    ToastUtils.showShort("请输入大于0的收款金额");
                    return;
                }
                if (((RadioButton) rgScanWay.getChildAt(1)).isChecked()) {
                    GatheringFragmentPermissionsDispatcher.showCameraWithPermissionCheck(this);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isFromOrder", false);
                    bundle.putString("orderPrice", money);
                    bundle.putString("payType", payType);
                    jumpActivity(bundle, QrcodeActivity.class);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCAN_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    String money = etMoney.getText().toString().trim();
                    mPresenter.requestScanPay(result, money);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.showShort("解析二维码失败");
                }
            }
        } else if (requestCode == REQUEST_CAMERA) {
            GatheringFragmentPermissionsDispatcher.showCameraWithPermissionCheck(this);
        }
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
        ToastUtils.showShort(msg);
    }

    @Override
    public void scanPaySuccess(PayResultEntity payResultEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("payResult", payResultEntity);
        bundle.putBoolean("isFromOrder", false);
        jumpActivity(bundle, PaySuccessActivity.class);
    }

    @Override
    public void paySuccess(PayResultEntity payResultEntity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("payResult", payResultEntity);
        bundle.putBoolean("isFromOrder", false);
        jumpActivity(bundle, PaySuccessActivity.class);
    }
}
