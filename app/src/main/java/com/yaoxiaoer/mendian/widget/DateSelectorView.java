package com.yaoxiaoer.mendian.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.blankj.utilcode.util.TimeUtils;
import com.yaoxiaoer.mendian.R;
import com.yaoxiaoer.mendian.base.BaseRelativeLayout;
import com.yaoxiaoer.mendian.utils.Order;
import com.yaoxiaoer.mendian.utils.Utils;
import java.text.SimpleDateFormat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Chenwy on 2018/2/27.
 */

public class DateSelectorView extends BaseRelativeLayout {
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.tv_yestoday)
    TextView tvYesToday;
    @BindView(R.id.tv_7_day)
    TextView tv7Day;
    @BindView(R.id.tv_30_day)
    TextView tv30Day;
    @BindView(R.id.tv_start_day)
    TextView tvStartDay;
    @BindView(R.id.tv_end_day)
    TextView tvEndDay;

    public DateSelectorView(Context context) {
        super(context);
    }

    public DateSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateSelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.date_selector_view;
    }

    @Override
    protected void init(AttributeSet attrs, int defStyleAttr) {

    }

    @OnClick({R.id.tv_today, R.id.tv_yestoday, R.id.tv_7_day, R.id.tv_30_day
            , R.id.tv_start_day, R.id.tv_end_day})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_today:
                setDefaultDayStyle();
                tvToday.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvToday.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                if (onDateChooseListener != null) {
                    String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
                    String startDay = Utils.getStartDateTime(nowString);
                    String endDay = Utils.getEndDateTime(nowString);
                    onDateChooseListener.onDateChoose(Order.TODAY, startDay, endDay);
                }
                break;
            case R.id.tv_yestoday:
                setDefaultDayStyle();
                tvYesToday.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvYesToday.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                if (onDateChooseListener != null) {
                    String startDay = Utils.getPastDateStartTime(1);
                    String endDay = Utils.getPastDateEndTime(1);
                    onDateChooseListener.onDateChoose(Order.YESTODAY, startDay, endDay);
                }
                break;
            case R.id.tv_7_day:
                setDefaultDayStyle();
                tv7Day.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tv7Day.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                if (onDateChooseListener != null) {
                    String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
                    String startDay = Utils.getPastDateStartTime(6);
                    String endDay = Utils.getEndDateTime(nowString);
                    onDateChooseListener.onDateChoose(Order._7DAY, startDay, endDay);
                }
                break;
            case R.id.tv_30_day:
                setDefaultDayStyle();
                tv30Day.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tv30Day.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                if (onDateChooseListener != null) {
                    String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
                    String startDay = Utils.getPastDateStartTime(29);
                    String endDay = Utils.getEndDateTime(nowString);
                    onDateChooseListener.onDateChoose(Order._30DAY, startDay, endDay);
                }
                break;
            case R.id.tv_start_day:
                setDefaultDayStyle();
                tvStartDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvStartDay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvEndDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvEndDay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                if (onDateChooseListener != null) {
                    String startDay = tvStartDay.getText().toString().trim();
                    String endDay = tvEndDay.getText().toString().trim();
                    onDateChooseListener.onDateChoose(Order.START_DATE,
                            Utils.getStartDateTime(startDay), Utils.getEndDateTime(endDay));
                }
                break;
            case R.id.tv_end_day:
                setDefaultDayStyle();
                tvStartDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvStartDay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvEndDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvEndDay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                if (onDateChooseListener != null) {
                    String startDay = tvStartDay.getText().toString().trim();
                    String endDay = tvEndDay.getText().toString().trim();
                    onDateChooseListener.onDateChoose(Order.END_DATE,
                            Utils.getStartDateTime(startDay), Utils.getEndDateTime(endDay));
                }
                break;
        }
    }

    public void initDate(int dayWhat, String startDay, String endDay) {

        String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        setDefaultDayStyle();

        switch (dayWhat) {
            case Order.TODAY:
                tvToday.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvToday.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvStartDay.setText(nowString);
                tvEndDay.setText(nowString);
                break;
            case Order.YESTODAY:
                tvYesToday.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvYesToday.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvStartDay.setText(nowString);
                tvEndDay.setText(nowString);
                break;
            case Order._7DAY:
                tv7Day.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tv7Day.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvStartDay.setText(nowString);
                tvEndDay.setText(nowString);
                break;
            case Order._30DAY:
                tv30Day.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tv30Day.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvStartDay.setText(nowString);
                tvEndDay.setText(nowString);
                break;
            case Order.START_DATE:
                tvStartDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvStartDay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvEndDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvEndDay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                if (startDay.contains(" ")) {
                    startDay = startDay.split(" ")[0];
                }
                if (endDay.contains(" ")) {
                    endDay = endDay.split(" ")[0];
                }
                tvStartDay.setText(startDay);
                tvEndDay.setText(endDay);
                break;
            case Order.END_DATE:
                tvStartDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvStartDay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                tvEndDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                tvEndDay.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                if (startDay.contains(" ")) {
                    startDay = startDay.split(" ")[0];
                }
                if (endDay.contains(" ")) {
                    endDay = endDay.split(" ")[0];
                }
                tvStartDay.setText(startDay);
                tvEndDay.setText(endDay);
                break;
        }
    }

    public void setStartDay(String startDay) {
        if (startDay.contains(" ")) {
            startDay = startDay.split(" ")[0];
        }
        tvStartDay.setText(startDay);
    }

    public void setEndDay(String endDay) {
        if (endDay.contains(" ")) {
            endDay = endDay.split(" ")[0];
        }
        tvEndDay.setText(endDay);
    }

    public void resetDay() {
        setDefaultDayStyle();
        tvToday.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        tvToday.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        String nowString = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd"));
        tvStartDay.setText(nowString);
        tvEndDay.setText(nowString);
        if (onDateChooseListener != null) {
            onDateChooseListener.onDateChoose(Order.TODAY,
                    Utils.getStartDateTime(nowString), Utils.getEndDateTime(nowString));
        }
    }

    private void setDefaultDayStyle() {
        tvToday.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvToday.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tvYesToday.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvYesToday.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tv7Day.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tv7Day.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tv30Day.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tv30Day.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tvStartDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvStartDay.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));

        tvEndDay.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_eb));
        tvEndDay.setTextColor(ContextCompat.getColor(getContext(), R.color.color_8));
    }

    private OnDateChooseListener onDateChooseListener;

    public void setOnDateChooseListener(OnDateChooseListener onDateChooseListener) {
        this.onDateChooseListener = onDateChooseListener;
    }

    public interface OnDateChooseListener {
        void onDateChoose(int chooseDateWhat, String startDay, String endDay);
    }
}
