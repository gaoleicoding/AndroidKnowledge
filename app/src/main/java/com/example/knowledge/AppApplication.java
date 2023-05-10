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

import com.example.knowledge.utils.LogUtil;
import com.example.knowledge.utils.TypeFaceUtil;
import com.tencent.smtt.sdk.QbSdk;

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
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                // 内核初始化完成，可能为系统内核，也可能为系统内核
            }

            /**
             * 预初始化结束
             * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
             * @param isX5 是否使用X5内核
             */
            @Override
            public void onViewInitFinished(boolean isX5) {

            }
        });
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
            LogUtil.d("LifecycleObserver", "应用回到前台");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onBackground() {
            LogUtil.d("LifecycleObserver", "应用退到后台");
        }
    }
}
