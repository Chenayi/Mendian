package com.yaoxiaoer.mendian.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.yaoxiaoer.mendian.BuildConfig;
import com.yaoxiaoer.mendian.event.PushOrderEvent;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Chenwy on 2018/2/26.
 */

public class PushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //收到自定义消息
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String msg = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            if (BuildConfig.DEBUG){
                Logger.e("收到透传消息：" + msg);
            }
            EventBus.getDefault().post(new PushOrderEvent());
        }
    }
}
