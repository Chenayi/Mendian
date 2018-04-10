package com.yaoxiaoer.mendian.mvp.presenter;

import android.content.Context;

import com.yaoxiaoer.mendian.base.BasePresenter;
import com.yaoxiaoer.mendian.di.scope.DialogScope;
import com.yaoxiaoer.mendian.http.BaseDownloadObserver;
import com.yaoxiaoer.mendian.http.HttpManager;
import com.yaoxiaoer.mendian.http.api.ApiService;
import com.yaoxiaoer.mendian.mvp.contract.UpgradeContract;
import com.yaoxiaoer.mendian.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Chenwy on 2018/3/8.
 */
@DialogScope
public class UpgradePresenter extends BasePresenter<UpgradeContract.View> {
    @Inject
    public UpgradePresenter(Context context, UpgradeContract.View view, HttpManager httpManager) {
        super(context, view, httpManager);
    }

    /**
     * 下载安装包
     *
     * @param url
     * @param apkName
     */
    public void downloadApk(String url, final String apkName) {
        mHttpManager.obtainRetrofitService(ApiService.class)
                .download(url)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .observeOn(Schedulers.computation())
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        return saveFile(responseBody.byteStream(), FileUtils.getApkFile(apkName));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseDownloadObserver<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    protected void onDownloadSuccess(File file) {
                        mView.downLoadSuccess(file);
                    }

                    @Override
                    protected void onDownloadError(int code, String msg) {
                        mView.onError(code, msg);
                    }
                });
    }

    /**
     * 保存安装包到本地
     *
     * @param is
     * @param apkFile
     * @return
     * @throws IOException
     */
    private File saveFile(InputStream is, File apkFile) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            if (apkFile.exists()) {
                apkFile.delete();
            }
            fos = new FileOutputStream(apkFile);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            return apkFile;

        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
