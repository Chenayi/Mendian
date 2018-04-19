package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;
import android.os.Vibrator;
import com.blankj.utilcode.util.AppUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.ActivityScope;
import com.yaoxiaoer.mendian.http.BaseObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.RxScheduler;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.MainContract;
import com.yaoxiaoer.mendian.mvp.entity.ApkInfoEntity;
import com.yaoxiaoer.mendian.mvp.entity.BaseResponse;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;

/**
 * Created by Chenwy on 2018/3/1.
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.View> {
    private SpeechSynthesizer mySynthesizer;
    private Vibrator mVibrator;

    @Inject
    public MainPresenter(Context context, MainContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 检查更新
     */
    public void checkUpgrade() {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .checkUpgrade()
                .compose(RxScheduler.<BaseResponse<ApkInfoEntity>>compose())
                .subscribe(new BaseObserver<ApkInfoEntity>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onHandleSuccess(ApkInfoEntity apkInfoEntity) {
                        int appVersionCode = AppUtils.getAppVersionCode();
                        ApkInfoEntity.Detail detail = apkInfoEntity.detail;
                        int newVersionCode = Integer.parseInt(detail.getVersionCode());

                        if (newVersionCode > appVersionCode) {
                            mView.upgrade(apkInfoEntity.detail);
                        }
                    }

                    @Override
                    protected void onHandleError(int code, String msg) {
                    }

                    @Override
                    protected void onHandleAfter() {
                    }
                });
    }

    /**
     * 震动提醒
     */
    public void vibrator(){
        long[] patter = {0, 300, 300, 300};
        if (mVibrator == null) {
            mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        }
        mVibrator.vibrate(patter, -1);
    }

    /**
     * 语音播报
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
