package com.example.knowledge;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Handler;
import android.webkit.WebView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;

import com.fifedu.lib_common_utils.log.LogUtils;
import com.example.knowledge.utils.TypeFaceUtil;

public class AppApplication extends Application {
    public static Context context;
    private static Handler mHandler;

    public static boolean isDebug = false;//是否是测试环境

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initWebView();
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mHandler = new Handler();

        getApkMode();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new MyLifecycleObserver());
        TypeFaceUtil.replaceSystemDefaultFont(this,TypeFaceUtil.fontPath_ARIAL);

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

    private void initWebView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName();
            WebView.setDataDirectorySuffix(processName);
        }
    }

    private class MyLifecycleObserver implements LifecycleObserver {

        // 方法名随便取，注解才是重点
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onForeground() {
            LogUtils.d("LifecycleObserver", "应用回到前台");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onBackground() {
            LogUtils.d("LifecycleObserver", "应用退到后台");
        }
    }
}
