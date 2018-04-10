package com.yaoxiaoer.mendian.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseAdapter;
import com.yaoxiaoer.mendian.base.BaseRelativeLayout;
import com.yaoxiaoer.mendian.helper.MyViewHolder;
import com.yaoxiaoer.mendian.mvp.entity.User;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Chenwy on 2018/3/1.
 */

public class LoginUserView extends BaseRelativeLayout {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.rl_username)
    RelativeLayout rlUsername;
    @BindView(R.id.rv_user_names)
    RecyclerView rvUserNames;

    private MyAdapter mMyAdapter;
    private boolean isArrowUp;
    private int marginTop;

    public LoginUserView(Context context) {
        super(context);
    }

    public LoginUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginUserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_user_view;
    }

    @Override
    protected void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray t = mContext.obtainStyledAttributes(attrs, R.styleable.LoginUserView);
        marginTop = t.getDimensionPixelSize(R.styleable.LoginUserView_marginTop, 0);
        t.recycle();
        init();
    }

    private void init() {
        LayoutParams layoutParams = (LayoutParams) rlUsername.getLayoutParams();
        layoutParams.topMargin = marginTop;
        rlUsername.setLayoutParams(layoutParams);

        mMyAdapter = new MyAdapter(new ArrayList<User>());
        rvUserNames.setLayoutManager(new LinearLayoutManager(mContext));
        rvUserNames.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String userName = mMyAdapter.getItem(position).getU_username();
                etUsername.requestFocus();
                etUsername.setText(userName);
                etUsername.setSelection(userName.length());
                hide();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isArrowUp) {
                hide();
                return true;
            }
        }
        return false;
    }

    private void hideKeyboard() {
        KeyboardUtils.hideSoftInput(this);
    }

    public void setDatas(List<User> users) {
        mMyAdapter.setNewData(users);
        etUsername.setText(users.get(0).getU_username());
        ivArrow.setVisibility(VISIBLE);
    }

    public void show() {
        arrowRotateUp();
        isArrowUp = true;
        int height = SizeUtils.getMeasuredHeight(rvUserNames);
        openAnim(rvUserNames, height);
    }

    public void hide() {
        arrowRotateDown();
        isArrowUp = false;
        closeAnimate(rvUserNames);
    }

    private void openAnim(View v, int height) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0, height);
        animator.start();
    }

    private void closeAnimate(final View view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    @OnClick({R.id.iv_arrow})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_arrow:
                hideKeyboard();
                if (isArrowUp) {
                    hide();
                } else {
                    show();
                }
                break;
        }

    }

    private void arrowRotateUp() {
        ivArrow.animate().setDuration(200).rotation(180).start();
    }

    private void arrowRotateDown() {
        ivArrow.animate().setDuration(200).rotation(0).start();
    }

    public String getUserName() {
        return etUsername.getText().toString().trim();
    }

    static class MyAdapter extends BaseAdapter<User, MyViewHolder> {

        public MyAdapter(@Nullable List<User> data) {
            super(R.layout.item_histtory_user, data);
        }

        @Override
        protected void convert(MyViewHolder helper, User user) {
            helper.setText(R.id.tv_userName, user.getU_username())
                    .setGone(R.id.line, helper.getLayoutPosition() < getData().size() - 1);
        }
    }
}
