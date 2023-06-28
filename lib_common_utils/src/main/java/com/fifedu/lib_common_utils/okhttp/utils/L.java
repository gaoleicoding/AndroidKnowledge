package com.fifedu.lib_common_utils.okhttp.utils;

import android.util.Log;

import com.fifedu.lib_common_utils.AppUtil;

/**
 * Created by gaolei on 2023/5/23.
 */
public class L {
    private static final boolean debug = AppUtil.isDebugMode();

    public static void e(String msg) {
        if (debug) {
            Log.e("OkHttp", msg);
        }
    }

}

