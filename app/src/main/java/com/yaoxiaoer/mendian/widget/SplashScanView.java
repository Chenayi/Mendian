package com.yaoxiaoer.mendian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.orhanobut.logger.Logger;
import com.yaoxiaoer.mendian.R;

/**
 * Created by Chenwy on 2018/2/26.
 */

public class SplashScanView extends RelativeLayout {
    private Context mContext;

    /**
     * 背景图标
     */
    private ImageView ivScanBg;

    /**
     * 移动线
     */
    private ImageView ivScanLine;

    /**
     * 默认背景图标的宽高
     */
    private int DEFAULT_SIZE;

    /**
     * 默认移动线的高度
     */
    private int DEFAULT_SCAN_LINE_HEIGHT;

    /**
     * 默认线移动的时长
     */
    private final int DETAULT_DURATION = 750;

    /**
     * 默认线移动的重复次数
     */
    private final int DEFAULT_REPEAT_COUNT = 4;

    /**
     * 顶部开始
     */
    private final int START_LOCATION_TOP = 1;

    /**
     * 底部开始
     */
    private final int START_LOCATION_BOTTOM = 2;

    /**
     * 宽高
     */
    private int size;
    private int scanLineHeight;
    private int duration;
    private int repeatCount;
    private int startLocation;

    /**
     * 记录移动的次数
     */
    private int curRepeatCount = 0;

    /**
     * 从下往上移动画
     */
    private Animation bottomToTopAnim;
    /**
     * 从上往下移动画
     */
    private Animation topToBottomAnim;


    private OnAnimEndListener onAnimEndListener;

    public SplashScanView(Context context) {
        this(context, null);
    }

    public SplashScanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        DEFAULT_SIZE = SizeUtils.dp2px(200f);
        DEFAULT_SCAN_LINE_HEIGHT = SizeUtils.dp2px(40f);

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.SplashScanView);
        size = t.getDimensionPixelSize(R.styleable.SplashScanView_size, DEFAULT_SIZE);
        scanLineHeight = t.getDimensionPixelSize(R.styleable.SplashScanView_scanLineHeight, DEFAULT_SCAN_LINE_HEIGHT);
        duration = t.getInteger(R.styleable.SplashScanView_duration, DETAULT_DURATION);
        repeatCount = t.getInteger(R.styleable.SplashScanView_repeatCount, DEFAULT_REPEAT_COUNT);
        startLocation = t.getInteger(R.styleable.SplashScanView_startLocation, START_LOCATION_BOTTOM);
        t.recycle();

        initScanBg();
        initScanLine();
        initScanAnim();
    }

    private void initScanBg() {
        ivScanBg = new ImageView(mContext);
        ivScanBg.setId(R.id.scan_bg);
        ivScanBg.setImageResource(R.mipmap.splash_logo);
        ivScanBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams layoutParams = new LayoutParams(size, size);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ivScanBg.setLayoutParams(layoutParams);
        addView(ivScanBg);
    }

    private void initScanLine() {
        ivScanLine = new ImageView(mContext);
        ivScanLine.setImageResource(R.mipmap.splash_scan);
        ivScanLine.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams layoutParams = new LayoutParams(size, scanLineHeight);
        if (startLocation == START_LOCATION_BOTTOM) {
            layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, ivScanBg.getId());
        } else if (startLocation == START_LOCATION_TOP){
            layoutParams.addRule(RelativeLayout.ALIGN_TOP, ivScanBg.getId());
        }
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ivScanLine.setLayoutParams(layoutParams);
        addView(ivScanLine);
    }

    private void initScanAnim() {
        int offset;
        if (startLocation == START_LOCATION_BOTTOM) {
            offset = scanLineHeight - size;
            bottomToTopAnim = new TranslateAnimation(0, 0
                    , 0, offset);
            topToBottomAnim = new TranslateAnimation(0, 0
                    , offset, 0);
        } else if (startLocation == START_LOCATION_TOP){
            offset = size - scanLineHeight;
            topToBottomAnim = new TranslateAnimation(0, 0
                    , 0, offset);
            bottomToTopAnim = new TranslateAnimation(0, 0
                    , offset, 0);
        }


        bottomToTopAnim.setDuration(duration);
        bottomToTopAnim.setFillAfter(true);
        topToBottomAnim.setDuration(duration);
        topToBottomAnim.setFillAfter(true);


        bottomToTopAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                curRepeatCount += 1;
                if (curRepeatCount >= repeatCount) {
                    if (onAnimEndListener != null) {
                        onAnimEndListener.onAnimEnd();
                    }
                } else {
                    ivScanLine.startAnimation(topToBottomAnim);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        topToBottomAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                curRepeatCount += 1;
                if (curRepeatCount >= repeatCount) {
                    if (onAnimEndListener != null) {
                        onAnimEndListener.onAnimEnd();
                    }
                } else {
                    ivScanLine.startAnimation(bottomToTopAnim);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startScanAnim(OnAnimEndListener onAnimEndListener) {
        this.onAnimEndListener = onAnimEndListener;
        // 播放动画
        if (startLocation == START_LOCATION_BOTTOM) {
            ivScanLine.startAnimation(bottomToTopAnim);
        } else {
            ivScanLine.startAnimation(topToBottomAnim);
        }
    }

    public interface OnAnimEndListener {
        void onAnimEnd();
    }
}
