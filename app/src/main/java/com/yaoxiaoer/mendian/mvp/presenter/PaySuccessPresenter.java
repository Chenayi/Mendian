package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.mvp.contract.PaySuccessContract;
import javax.inject.Inject;

/**
 * Created by Chenwy on 2018/3/16.
 */

public class PaySuccessPresenter extends DelayFinishPresenter<PaySuccessContract.View> {
    /**
     * 语音播报
     */
    private SpeechSynthesizer mySynthesizer;

    @Inject
    public PaySuccessPresenter(Context context, PaySuccessContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    @Override
    protected void updateSecond(int second) {
        mView.updateSecond(second);
    }

    @Override
    protected void close() {
        mView.close();
    }

    /**
     * 语音播报
     *
     * @param msg
     */
    public void playSpeech(String msg) {
        if (mySynthesizer == null) {
            mySynthesizer = SpeechSynthesizer.createSynthesizer(mContext, null);
            //设置发音人
            mySynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
            //设置音调
            mySynthesizer.setParameter(SpeechConstant.PITCH, "50");
            //设置音量
            mySynthesizer.setParameter(SpeechConstant.VOLUME, "100");
        }
        //播放语音
        mySynthesizer.startSpeaking(msg, null);
    }
}
