package com.fifedu.lib_common_utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by gaolei on 2023/5/27.
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(final String msg) {
        showToast(ContextProvider.getAppContext(), msg, false);
    }

    /**
     * 立即并连续弹吐司
     */
    public static void showToast(final Context mContext, final String msg) {
        showToast(mContext, msg, false);

    }

    public static void showToast(final Context mContext, final String msg, boolean isLong) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                closeToast();
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, "", isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                }
                mToast.setText(msg);
                mToast.show();
            }
        });
    }

    public static void showCenterToast(final Context mContext, final String msg) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                closeToast();
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
                    mToast.setGravity(Gravity.CENTER, 0, 0);
                }
                mToast.setText(msg);
                mToast.show();

            }
        });
    }

    /**
     * 立即并连续弹吐司（显示自定义视图的toast（一条提示信息））
     *
     * @param mContext
     * @param msg
     */
    public static void showToastCustom(final Context mContext, String msg) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                closeToast();
                final View view = View.inflate(mContext, R.layout.lib_utils_toast1,
                        null);
                TextView tv_msg = (TextView) view.findViewById(R.id.tv_toast_msg);
                tv_msg.setText(msg);
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
                }
                mToast.setGravity(Gravity.CENTER, 0, 0);// 显示在屏幕中部
                mToast.setView(view);// 显示自定义的view
                mToast.show();
            }
        });
    }

    public static void showToastCustomShort(final Context mContext, int res,
                                            String msg1, String msg2) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                closeToast();
                final View view = View.inflate(mContext, R.layout.lib_utils_toast,
                        null);
                ImageView iv_img = view.findViewById(R.id.iv_toast_img);
                TextView tv_msg1 = view.findViewById(R.id.tv_toast_msg1);
                TextView tv_msg2 = view.findViewById(R.id.tv_toast_msg2);
                if (res != 0) {
                    iv_img.setVisibility(View.VISIBLE);
                    iv_img.setImageResource(res);
                }
                tv_msg1.setText(msg1);

                if (!TextUtils.isEmpty(msg2)) {
                    tv_msg2.setText(msg2);
                }

                if (mToast == null) {
                    mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
                }
                mToast.setGravity(Gravity.CENTER, 0, 0);// 显示在屏幕中部
                mToast.setView(view);// 显示自定义的view
                mToast.show();
                mainThread.postDelayed(new Runnable() {
                    public void run() {
                        if (mToast != null)
                            mToast.cancel();
                    }
                }, 1000);
            }
        });
    }

    /**
     * 立即并连续弹吐司（显示自定义视图的toast（两条提示信息））
     */
    public static void showToastCustom(final Context mContext, int res,
                                       String msg1, String msg2) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                closeToast();
                final View view = View.inflate(mContext, R.layout.lib_utils_toast,
                        null);
                ImageView iv_img = view.findViewById(R.id.iv_toast_img);
                TextView tv_msg1 = view.findViewById(R.id.tv_toast_msg1);
                TextView tv_msg2 = view.findViewById(R.id.tv_toast_msg2);
                if (res != 0) {
                    iv_img.setVisibility(View.VISIBLE);
                    iv_img.setImageResource(res);
                }
                tv_msg1.setText(msg1);

                if (!TextUtils.isEmpty(msg2)) {
                    tv_msg2.setText(msg2);
                }

                if (mToast == null) {
                    mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
                }
                mToast.setGravity(Gravity.CENTER, 0, 0);// 显示在屏幕中部
                mToast.setView(view);// 显示自定义的view
                mToast.show();
            }
        });
    }


    public static void closeToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }
}
