package com.fifedu.lib_common_utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.fifedu.lib_common_utils.log.LogUtils;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * Created by gaolei on 2023/5/27.
 */

public class SystemUtil {

    /**
     * 没有连接网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;

    public static boolean isPad(Context context) {
        boolean isPad = (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y); // 屏幕尺寸
        return isPad || screenInches >= 7.0;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取屏幕分辨率x
     */
    public static int getScreenWidth(Context context) {
        return getScreenSize(context)[0];
    }

    /**
     * 获取屏幕分辨率y
     */
    public static int getScreenHeight(Context context) {
        return getScreenSize(context)[1];
    }

    /**
     * 获取屏幕尺寸
     *
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static int[] getScreenSize(Context context) {
        if (context == null) return new int[]{0, 0};
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        if (context instanceof Activity) {
            ((Activity) context).getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        }
        return new int[]{dm.widthPixels, dm.heightPixels};
    }

    //用于设置app字体不受系统影响
    public static Resources setAppFont(Resources resources) {
        if (resources != null) {
            Configuration config = resources.getConfiguration();
            if (config != null && config.fontScale != 1.0f) {
                config.fontScale = 1.0f;
                resources.updateConfiguration(config, resources.getDisplayMetrics());
            }
        }
        return resources;
    }

    //获取app sha1值
    private static String getAppSha1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte b : publicKey) {
                String appendString = Integer.toHexString(0xFF & b)
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int dp2px(int dpValue) {
        return dp2px(ContextProvider.getAppContext(), dpValue);
    }

    public static boolean isEqualOrAboveVersion(int targetVersion) {
        return Build.VERSION.SDK_INT >= targetVersion;
    }

    public static int dp2px(Context context, int dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    //判断是否是小米三手机，原因是小米三手机进行音频的压缩的时候，会莫名其妙崩溃，原因未知
    public static boolean isMi3() {
        String phoneName = Build.MODEL;
        return !TextUtils.isEmpty(phoneName) && (phoneName.contains("MI") || phoneName.contains("mi")) && phoneName.contains("3");
    }

    public static int getStatusBarHeightCompat(Context context) {
        int result = dp2px(ContextProvider.getAppContext(), 25);
        if (context == null) return result;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }

        return result;
    }


    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ContextProvider.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断是否是wifi
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ContextProvider.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isNetConnected() {
        return isNetworkAvailable(ContextProvider.getAppContext());
    }

    /**
     * 检查网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        return networkinfo != null && networkinfo.isAvailable();
    }

    /**
     * 获取当前网络连接类型
     */
    public static String getNetworkState() {
        return getNetworkState(ContextProvider.getAppContext());
    }

    public static String getNetworkState(Context context) {
        // 获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 如果当前没有网络
        if (null == connManager) {
            return "OTHER";
        }
        // 获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return "OTHER";
        }
        // 判断是不是连接的是不是wifi
        NetworkInfo wifiInfo = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED
                        || state == NetworkInfo.State.CONNECTING) {
                    return "WIFI";
                }
            }
        }
        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED
                        || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        // 如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return "N2G";
                        // 如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return "N3G";
                        // 如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return "N4G";
                        default:
                            // 中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                    || strSubTypeName.equalsIgnoreCase("WCDMA")
                                    || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return "N3G";
                            } else {
                                return "WIFI";
                            }
                    }
                }
            }

        }
        return "OTHER";
    }

    /**
     * 获取信号连接状态 :无连接，wifi,mobile
     */
    public static int getNetWorkType() {
        return getNetWorkType(ContextProvider.getAppContext());
    }

    public static int getNetWorkType(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                LogUtils.d("NetUtil==", "NETWORK_WIFI==");
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                LogUtils.d("NetUtil==", "TYPE_MOBILE==");

                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    public static void isMobileNetWorkToast(Context context) {
        if (getNetWorkType(context) == NETWORK_MOBILE) {
            ToastUtil.showToast(CommonUtils.getString(R.string.lib_utils_msg_info_network));
        }
    }

    public static void isMobileNetWorkToast() {

        isMobileNetWorkToast(ContextProvider.getAppContext());

    }

}
