package com.yaoxiaoer.mendian.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Chenwy on 2018/3/2.
 * File 工具类
 */

public class FileUtils {
    public static final String DIR = Environment.getExternalStorageDirectory() + "/yaoxiaoer";

    public static File getApkFile(String apkName) {
        boolean mounted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!mounted) {
            return null;
        }
        File dir = new File(DIR + "/apk");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, apkName);
        return file;
    }

    public static File cacheDir() {
        boolean mounted = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!mounted) {
            return null;
        }
        File dir = new File(DIR + "/cache");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
