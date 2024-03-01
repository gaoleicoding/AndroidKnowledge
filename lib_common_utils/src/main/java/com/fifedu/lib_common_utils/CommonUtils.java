package com.fifedu.lib_common_utils;

import static android.os.Environment.MEDIA_MOUNTED;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

//封装常用的工具类
public class CommonUtils {

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
     * 根据dp转px
     */
    public static int dp2px(int dpValue) {
        return dp2px(ContextProvider.getAppContext(), dpValue);
    }

    public static int dp2px(Context context, int dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据px转dp
     */
    public static int px2dp(float pxValue) {
        return px2dp(ContextProvider.getAppContext(), pxValue);
    }

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

    /**
     * 动态设置view的margin
     *
     * @param l 距左
     * @param t 距上
     * @param r 距右
     * @param b 距下
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v
                    .getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    /**
     * 跳转到应用设置界面
     */
    public static void jumpToAppSettings(Activity activity, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 设置textview的字体(显示音标)
     */
    public static void setTypeface(TextView tv_content) {
        setCustomTypeface(tv_content, "fonts/segoeui.ttf");

    }

    public static void setCustomTypeface(TextView tv_content, String fontPath) {
        Typeface typeFace = Typeface.createFromAsset(ContextProvider
                .getAppContext().getAssets(), fontPath);// 设置字体
        tv_content.setTypeface(typeFace);
    }

    /**
     * 将视图从父视图中移除
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * 子线程修改UI
     */
    public static void runOnUIThread(Runnable runnable) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        mainThread.post(runnable);
    }

    /**
     * 根据listview的列表项重新计算高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            if (listItem != null) {
                listItem.measure(0, 0);
                // 统计所有子项的总高度
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        // 有存储的SDCard
        return state.equals(MEDIA_MOUNTED);
    }

    /**
     * 获取sd卡剩余空间大小（单位M）
     */
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 设置默认资源下载目录
     */
    public static String getDataPath() {
        return ContextProvider.getAppContext().getExternalFilesDir("download").getAbsolutePath();
    }

    /**
     * 加载drawable中的图片
     */
    public static void loadDrawable(int ResId, ImageView imageView) {
        imageView.setImageResource(ResId);

    }

    /**
     * 删除文件夹及文件夹下的所有文件
     */
    public static boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(file, child));
                    if (!success) {
                        return false;
                    }
                }
            } else {
                // 目录为空，可以删除
                return true;
            }
        }
        // 目录此时为空，可以删除
        return file.delete();
    }

    public static String getTextFromTXT(Context context, String fileName) {
        try {
            InputStream in = context.getAssets().open(fileName);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            return new String(buffer, "GB2312");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转为M显示
     */
    public static String bytes2Mb(long bytes) {

        BigDecimal filesize = new BigDecimal(bytes);

        BigDecimal megabyte = new BigDecimal(1024 * 1024);

        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();

        return returnValue + "M";

    }

    /**
     * 获取文件夹大小
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        if (f.isDirectory()) {
            File[] flist = f.listFiles();
            for (File file : flist) {
                if (file.isDirectory()) {
                    size = size + getFileSize(file);
                } else {
                    size = size + file.length();
                }
            }
        } else {
            size = size + f.length();
        }
        return size;
    }

    /**
     * 判断指定路径的文件夹是否存在且不为空
     *
     * @param directoryPath 文件夹路径
     * @return 如果文件夹存在并且不为空则范围 true， 反之返回 false
     */
    public static boolean whetherDirectoryExists(String directoryPath) {
        File dfile = new File(directoryPath);

        try {
            return dfile.exists()
                    && CommonUtils
                    .getFileSize(dfile) > 100;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int getStrLength(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 1;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String phoneNumber) {
        String PHONE_NUMBER_PATTERN = "^1[3456789]\\d{9}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        return pattern.matcher(phoneNumber).matches();
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * 获取屏幕尺寸
     *
     * @param activity Activity
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static int[] getScreenSize(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    /**
     * 处理富文本，替换<P></P>和\n标签换成</br>
     */
    public static String dealHtmlTxt(String str) {
        String txt = "";
        if (!TextUtils.isEmpty(str)) {
            txt = str.replace("\n", "<br/>").replace("<p>", "").replace("</p>", "<br/>");
        }
        return txt;
    }

    /**
     * 处理富文本，用于高亮展示
     */
    public static String changeHtmlTxt(String str) {
        String txt = "";
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("<span")) {
                str = "<@>" + str;//防止无法高亮展示
            }
            txt = str.replace("<span", "<span_custom").replace("</span>", "</span_custom>");
        }
        return txt;
    }

    /**
     * 判断字符串是不是int格式防止NumberFormatException
     */
    public static boolean isInteger(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            return pattern.matcher(s).matches();
        }
    }

    public static boolean isZhTxt(String str) {
        return !TextUtils.isEmpty(str) && str.matches("[\u4E00-\u9FA5]+");
    }

    // base64编码的字符串只包含大消息字母（A-Z，a-z）、数字0-9、+、/、=这64个字符
    public static boolean isValidBase64(String base64String) {
        try {
            Base64.decode(base64String.getBytes(), Base64.DEFAULT);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static void changeKeywordColor(TextView textView, String keyword, String string, int color) {
        if (keyword == null || keyword.trim().length() == 0) return;
        if (string == null || string.trim().length() == 0) return;
        if (!string.contains(keyword)) return;
        int start = string.indexOf(keyword);
        int end = start + keyword.length();
        if (end != 0 && start != -1) {
            final SpannableStringBuilder style = new SpannableStringBuilder();
            style.append(string);
            //设置部分文字颜色
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
            style.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(style);

        }
    }

    //判断是否是整数
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;

    }
}
