package com.fifedu.lib_common_utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by gaolei on 2023/5/27.
 */
public class AppUtil {

    /**
     * 获取当前程序包名
     *
     * @return 程序包名
     */
    public static String getPackageName() {
        return ContextProvider.getAppContext().getPackageName();
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getAppProvider(String provider) {
        return ContextProvider.getAppContext().getPackageName() + "." + provider;
    }

    public static String getAppProvider(Context context, String provider) {
        return context.getPackageName() + "." + provider;
    }

    /**
     * 获取程序版本信息
     *
     * @return 版本名称
     */
    public static String getVersionName() {
        return getVersionName(ContextProvider.getAppContext());
    }

    public static String getVersionName(Context context) {
        String versionName = "";
        String pkName = context.getPackageName();
        try {
            versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionName;
    }

    /**
     * 获取程序版本号
     *
     * @return 版本号
     */
    public static int getVersionCode() {
        return getVersionCode(ContextProvider.getAppContext());
    }

    public static int getVersionCode(Context context) {
        int versionCode = -1;
        String pkName = context.getPackageName();
        try {
            versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionCode;
    }

    public static boolean isDebugMode() {
        try {
            ApplicationInfo info = ContextProvider.getAppContext().getApplicationInfo();
            return info != null && (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isWeChatInstalled(Context context) {
        return checkApkExist(context, "com.tencent.mm");
    }

    public static Handler getMainHandler() {
        return new Handler(ContextProvider.getAppContext().getMainLooper());
    }

    public static Application currentApplication() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method application = activityThreadClass.getDeclaredMethod("currentApplication");
            application.setAccessible(true);
            return (Application) application.invoke(null);
        } catch (Exception e) {
            return null;
        }
    }
}