package com.yaoxiaoer.mendian.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.yaoxiaoer.mendian.BuildConfig;
import com.yaoxiaoer.mendian.C;
import com.yaoxiaoer.mendian.event.AccountsFreeingEvent;
import com.yaoxiaoer.mendian.event.PushOrderEvent;
import com.yaoxiaoer.mendian.mvp.entity.ReceiverEntity;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Chenwy on 2018/2/26.
 */

public class PushReceiver extends BroadcastReceiver {
    /**
     * 新订单
     */
    private final String CODE_NEW_ORDER = "333";
    /**
     * 多个账户冻结（按门店id）
     */
    private final String CODE_ACCOUNTS_FREEING_STORE = "555";

    /**
     * 单个账户冻结（按用户id）
     */
    private final String CODE_ACCOUNTS_FREEING_SINGLE_USER = "111";

    @Override
    public void onReceive(Context context, Intent intent) {
        //收到自定义消息
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            if (BuildConfig.DEBUG) {
                Logger.e("收到透传消息：" + msg);
            }
            try {
                ReceiverEntity entity = new Gson().fromJson(msg, ReceiverEntity.class);
                if (!TextUtils.isEmpty(entity.code)) {
                    //收到新订单
                    if (entity.code.equals(CODE_NEW_ORDER)) {
                        EventBus.getDefault().post(new PushOrderEvent());
                    }
                    //多个账户冻结（按门店id）
                    else if (entity.code.equals(CODE_ACCOUNTS_FREEING_STORE)) {
                        EventBus.getDefault().post(new AccountsFreeingEvent(entity.message));
                    }
                    //单个用户被冻结（按用户id）
                    else if (entity.code.equals(CODE_ACCOUNTS_FREEING_SINGLE_USER)){
                        if (entity.uid != null && entity.uid.equals(C.USER_ID+"")){
                            EventBus.getDefault().post(new AccountsFreeingEvent(entity.message));
                        }
                    }
                }
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Logger.e("非json格式");
                }
            }


        }
    }
}
