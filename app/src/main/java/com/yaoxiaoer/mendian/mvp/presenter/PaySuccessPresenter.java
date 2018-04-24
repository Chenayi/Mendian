package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.mvp.contract.PaySuccessContract;

import javax.inject.Inject;

/**
 * Created by Chenwy on 2018/3/16.
 */

public class PaySuccessPresenter extends DelayFinishPresenter<PaySuccessContract.View> {

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
    public void playSpeech(SpeechSynthesizer speechSynthesizer, String msg) {
        if (speechSynthesizer != null && !TextUtils.isEmpty(msg)) {
            //声量
            speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
            speechSynthesizer.initTts(TtsMode.MIX);
            speechSynthesizer.speak(msg);
        }
    }
}
