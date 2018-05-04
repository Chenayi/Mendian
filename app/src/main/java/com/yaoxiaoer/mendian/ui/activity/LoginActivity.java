package com.yaoxiaoer.mendian.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.OnKeyboardListener;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseActivity;
import com.yaoxiaoer.mendian.di.component.AppComponent;
import com.yaoxiaoer.mendian.di.component.DaggerLoginComponent;
import com.yaoxiaoer.mendian.di.module.LoginModule;
import com.yaoxiaoer.mendian.mvp.contract.LoginContract;
import com.yaoxiaoer.mendian.mvp.entity.User;
import com.yaoxiaoer.mendian.mvp.presenter.LoginPresenter;
import com.yaoxiaoer.mendian.widget.LoginUserView;
import com.yinglan.keyboard.HideUtil;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Chenwy on 2018/1/31.
 * 登录
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.et_password)
    EditText etPwd;
    @BindView(R.id.login_user_view)
    LoginUserView loginUserView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initImmersinBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.fitsSystemWindows(true, R.color.colorPrimary)
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                .setOnKeyboardListener(new OnKeyboardListener() {
                    @Override
                    public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                        if (isPopup) {
                            loginUserView.hide();
                        }
                    }
                })
                .init();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initDatas() {
        HideUtil.init(this);
        mPresenter.findCacheUserNames();
    }

    @OnClick({R.id.btn_login})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                KeyboardUtils.hideSoftInput(this);
                loginUserView.hide();
                String userName = loginUserView.getUserName();
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    ToastUtils.showShort("请输入用户名");
                    return;
                }

                if (TextUtils.isEmpty(pwd)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                mPresenter.login(userName, pwd);
                break;
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
    public void showCacheUsers(List<User> users) {
        loginUserView.setDatas(users);
        etPwd.requestFocus();
    }

    @Override
    public void loginSuccess() {
        jumpActivity(MainActivity.class);
        finish();
    }
}
