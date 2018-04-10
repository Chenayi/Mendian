package com.yaoxiaoer.mendian.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseAdapter;
import com.yaoxiaoer.mendian.base.BaseRelativeLayout;
import com.yaoxiaoer.mendian.helper.MyViewHolder;
import com.yaoxiaoer.mendian.mvp.entity.CashierEntity;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * Created by Chenwy on 2018/2/26.
 */

public class CashierFilterView extends BaseRelativeLayout {

    @BindView(R.id.tv_cashier)
    TextView tvCashier;
    @BindView(R.id.rv_cashier)
    RecyclerView rvCashier;
    @BindView(R.id.rl_choose)
    RelativeLayout rlChoose;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;

    private MyAdapter mMyAdapter;
    private CashierEntity.NickNameList mCashier;
    private boolean isShowList;

    public CashierFilterView(Context context) {
        super(context);
    }

    public CashierFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CashierFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cashier_filter_view;
    }

    @Override
    protected void init(AttributeSet attrs, int defStyleAttr) {
        mMyAdapter = new MyAdapter(new ArrayList<CashierEntity.NickNameList>());
        rvCashier.setLayoutManager(new LinearLayoutManager(mContext));
        rvCashier.setAdapter(mMyAdapter);
        mMyAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mCashier = mMyAdapter.getItem(position);
                if (position == 0) {
                    tvCashier.setText("全部");
                } else {
                    tvCashier.setText(mCashier.u_nickname);
                }
                arrowRotateDown();
                rvCashier.setVisibility(GONE);
                isShowList = false;

                if (onCashierStatusListener != null) {
                    onCashierStatusListener.onCashierChoose(mCashier);
                }
            }
        });

        rlChoose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowList) {
                    hide();
                } else {
                    show();

                }
            }
        });
    }

    public void hide() {
        arrowRotateDown();
        closeAnimate(rvCashier);
        isShowList = false;
    }

    public void show() {
        arrowRotateUp();
        int height = SizeUtils.getMeasuredHeight(rvCashier);
        openAnim(rvCashier, height);
        isShowList = true;
    }

    private void openAnim(View v, int height) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0, height);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (onCashierStatusListener != null) {
                    onCashierStatusListener.onCashierOpen();
                }
            }
        });
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

    private void arrowRotateUp() {
        ivArrow.animate().setDuration(200).rotation(180).start();
    }

    private void arrowRotateDown() {
        ivArrow.animate().setDuration(200).rotation(0).start();
    }

    public void setDatas(List<CashierEntity.NickNameList> cashiers, CashierEntity.NickNameList cashier) {
        if (cashiers != null) {
            mMyAdapter.setNewData(cashiers);
        }

        if (cashier != null) {
            tvCashier.setText(cashier.u_nickname);
            mCashier = cashier;
        } else {
            tvCashier.setText("全部");
        }
    }

    static class MyAdapter extends BaseAdapter<CashierEntity.NickNameList, MyViewHolder> {

        public MyAdapter(List<CashierEntity.NickNameList> data) {
            super(R.layout.item_cashier, data);
        }

        @Override
        protected void convert(MyViewHolder helper, CashierEntity.NickNameList item) {
            if (helper.getLayoutPosition() == 0) {
                helper.setText(R.id.tv_cashier, "全部");
            } else {
                String nickname = item.u_nickname;
                if (!TextUtils.isEmpty(nickname)) {
                    helper.setText(R.id.tv_cashier, nickname);
                }
            }
        }
    }

    public void resetChooseCashier() {
        tvCashier.setText("全部");
        mCashier = null;
        if (onCashierStatusListener != null) {
            onCashierStatusListener.onCashierChoose(mCashier);
        }
    }

    private OnCashierStatusListener onCashierStatusListener;

    public void setOnCashierStatusListener(OnCashierStatusListener onCashierStatusListener) {
        this.onCashierStatusListener = onCashierStatusListener;
    }

    public interface OnCashierStatusListener {
        void onCashierOpen();

        void onCashierChoose(CashierEntity.NickNameList cashier);
    }
}
