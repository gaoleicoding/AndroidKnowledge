package com.example.knowledge.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.knowledge.AppApplication;

public class ToastUtil {
    public static Toast mToast;

    /**
     * 立即并连续弹吐司
     *
     * @param mContext
     * @param text
     */
    public static void showToast(final Context mContext, final String text) {
        if (mContext != null) {
            closeToast();

            try {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
                }
                mToast.setText(text);
                mToast.show();

            } catch (Exception e) {
                runOnUIThread(() -> Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show());
            }
        }


    }

    public static void runOnUIThread(Runnable runnable) {
        AppApplication.getMainHandler().post(runnable);
    }


    public static void closeToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

}
