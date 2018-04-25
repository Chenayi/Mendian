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
    private SpeechSynthesizer mySynthesizer;

    @Inject
    public PaySuccessPresenter(Context context, PaySuccessContract.View view, HttpManager httpManager, SpeechSynthesizer mySynthesizer) {
        super(context, view, httpManager);
        this.mySynthesizer = mySynthesizer;
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
        if (mySynthesizer != null && !TextUtils.isEmpty(msg)) {
            //声量
            mySynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");
            mySynthesizer.initTts(TtsMode.ONLINE);
            mySynthesizer.speak(msg);
        }
    }
}
