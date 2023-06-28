package com.fifedu.lib_common_utils.log;

import android.util.Log;

import com.fifedu.lib_common_utils.AppUtil;
import com.fifedu.lib_common_utils.BuildConfig;

/**
 * @author bff007
 * @date 2017/8/24
 */
public class LogUtils {

    private static boolean isDebug = AppUtil.isDebugMode();

    public static void setDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }

    /**
     * 截断输出日志 防止日志打印不全
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            if (tag == null || tag.length() == 0
                    || msg == null || msg.length() == 0) {
                return;
            }

            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.d(tag, msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.d(tag, logContent);
                }
                Log.d(tag, msg);// 打印剩余日志
            }
        }
    }

    /**
     * 截断输出日志 防止日志打印不全
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            if (tag == null || tag.length() == 0
                    || msg == null || msg.length() == 0) {
                return;
            }

            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.e(tag, msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.e(tag, logContent);
                }
                Log.e(tag, msg);// 打印剩余日志
            }
        }
    }

}
