package com.example.knowledge;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Handler;

public class AppApplication extends Application {
    private static Context context;
    private static Handler mHandler;

    public static boolean isDebug = false;//是否是测试环境

    @Override
    public void onCreate() {
        context = this;
        mHandler = new Handler();

        //初始化一次防止静态变量被回收
        super.onCreate();
        getApkMode();
    }

    public void getApkMode() {
        try {
            ApplicationInfo info = getApplicationInfo();
            isDebug = info != null && (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
        }
    }

    public static Handler getMainHandler() {
        return mHandler;
    }
}
