package com.fifedu.lib_common_utils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

//封装常用的工具类
public class CommonUtil {


    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

    public static String getCurrentTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return sdf.format(date);
    }

    /**
     * 设置textview的字体
     */
    public static void setTypeface(TextView tv_content, String fontPath) {
        Typeface typeFace = Typeface.createFromAsset(ContextProvider.getAppContext().getAssets(), fontPath);
        tv_content.setTypeface(typeFace);
    }

    /**
     * 根据dp转px
     */
    public static int dp2px(Context context, int dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据px转dp
     */
    public static int px2dp(Context context, float pxValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    /**
     * 获取dimen文件中定义的dp值
     */
    public static float getDimens(int resId) {
        return ContextProvider.getAppContext().getResources().getDimension(resId);
    }

    /**
     * 获取string文件中定义的汉字
     */
    public static String getString(int resId) {
        return ContextProvider.getAppContext().getResources().getString(resId);
    }

    /**
     * 获取color文件中定义的颜色
     */
    public static int getColor(int resId) {
        return ContextProvider.getAppContext().getResources().getColor(resId);
    }

    /**
     * 获取drawable文件中定义的图片
     */
    public static Drawable getDrawable(int resId) {
        return ContextProvider.getAppContext().getResources().getDrawable(resId);
    }

    /**
     * 打卡软键盘
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
