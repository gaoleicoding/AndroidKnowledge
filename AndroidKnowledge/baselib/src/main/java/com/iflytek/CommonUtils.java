package com.iflytek;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//封装常用的工具类
public class CommonUtils {


    /**
     * 动态设置view的margin
     *
     * @param v
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
     * 处理富文本，替换<P></P>和\n标签换成</br>
     *
     * @param str
     * @return
     */
    public static String dealHtmlTxt(String str) {
        String txt = "";
        if (!TextUtils.isEmpty(str)) {
            txt = str.replace("\n", "<br/>").replace("<p>", "").replace("</p>", "<br/>");
        }
        return txt;
    }

    /**
     * 根据dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, int dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dpValue * density + 0.5f);
        return px;
    }

    /**
     * 根据px转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }

    /**
     * change sp to px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * 将视图从父视图中移除
     *
     * @param view
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }

    /**
     * 根据listview的列表项重新计算高度
     *
     * @param listView
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
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取sd卡剩余空间大小（单位M）
     *
     * @return
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
     * 获取外置SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                // 获得命令执行后在控制台的输出信息
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        String result = strArray[1].replace("/.android_secure",
                                "");
                        return result;
                    }
                }
                // 检查命令是否执行失败。
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0表示正常结束，1：非正常结束
                }
            }
            inBr.close();
            in.close();
        } catch (Exception e) {

            return Environment.getExternalStorageDirectory().getPath();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }


    /**
     * 删除文件夹及文件夹下的所有文件
     *
     * @return
     */
    public static boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(file, children[i]));
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
            String str = new String(buffer, "GB2312");
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转为M显示
     *
     * @param bytes
     * @return
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
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        if (f.isDirectory()) {
            File flist[] = f.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSize(flist[i]);
                } else {
                    size = size + flist[i].length();
                }
            }
        } else {
            size = size + f.length();
        }
        return size;
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
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动号段:   134 135 136 137 138 139 147 148 150 151 152 157 158 159  165 172 178 182 183 184 187 188 198
         * 联通号段:   130 131 132 145 146 155 156 166 170 171 175 176 185 186
         * 电信号段:   133 149 153 170 173 174 177 180 181 189  191  199
         * 虚拟运营商: 170
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "^((13[0-9])|(14[5,6,7,9])|(15[^4])|(16[5,6])|(17[0-9])|(18[0-9])|(19[1,8,9]))\\d{8}$";
        return mobiles.matches(telRegex);
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 对密码做一下处理
     *
     * @param psw
     * @return
     */
    public static String getChangePsw(String psw) {
        String temStr = "";
        char[] strs = psw.toCharArray();
        for (int i = 0; i < strs.length; i++) {
            temStr = temStr + strs[i] + CommonUtils.getRandomCharAndNumr();
        }
        return temStr;
    }

    /**
     * 获取随机字母或者数字
     *
     * @return
     */
    public static String getRandomCharAndNumr() {
        Random random = new Random();
        boolean b = random.nextBoolean();
        if (b) { // 字符
            return (char) (65 + random.nextInt(26)) + "";// 取得大写字母
        } else { // 数字
            return String.valueOf(random.nextInt(10)) + "";
        }

    }

    /**
     * 给指定题目挖空
     *
     * @param text
     * @return
     */
    public static String hollowTxt(String text) {
        Pattern p = Pattern.compile("\\{.*?\\}");// 查找规则公式中大括号以内的字符
        Matcher m = p.matcher(text);
        while (m.find()) {// 遍历找到的所有大括号中对应的内容
            String param = m.group();
            String[] params = param.split(" ");
            String tempParam = "";
            for (int i = 0; i < params.length; i++) {
                // 将单词替换为_
                if (!TextUtils.isEmpty(params[i])) {
                    // 只对单词进行挖空
                    if (i == params.length - 1) {
                        tempParam = tempParam + "____";// 最后一个单词不加空格
                    } else {
                        tempParam = tempParam + "____ ";
                    }
                }
            }
            if (!TextUtils.isEmpty(tempParam)) {
                text = text.replace(param, tempParam);
            }
        }
        return text;
    }

    /**
     * 处理带数字的评测文本，防止评测失败
     *
     * @param tempSentence
     * @return
     */
    public static String changeTxtWithNum(String tempSentence) {
//        String[] strs = tempSentence.split(" ");
//        tempSentence = "";
//        for (int i = 0; i < strs.length; i++) {
//            if (!TextUtils.isEmpty(strs[i]) && strs[i].matches("^.*\\d{1}.*$")) {// 针对AB1234，12ABC345这种类型
//                // 对句子中的单个单词进行处理，数字和字母之间加句号
//                String[] ss = strs[i].split("\\d+");
//                for (int x = 0; x < ss.length; x++) {
//                    if (ss[x].matches("[a-zA-Z]+")) {
//                        // 纯英文
//                        strs[i] = strs[i].replace(ss[x], "." + ss[x] + ".");
//                    }
//
//                }
//            }
//            if (!TextUtils.isEmpty(strs[i])
//                    && (strs[i].replace(".", "").matches("[0-9]+") || (strs[i]
//                    .replace(",", "").matches("[0-9]+")))) {// 针对纯数字123,12.45,12,000等类型
//                strs[i] = "." + strs[i] + ".";
//            }
//            tempSentence = tempSentence
//                    + strs[i].replace("...", ".").replace("..", ".") + " ";
//        }
        return tempSentence;
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
     * 获取当前日期（格式为 yyyy-MM-dd）
     *
     * @return
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 处理富文本，用于高亮展示
     *
     * @param str
     * @return
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
     * 处理富文本，用于替换颜色
     * 目前只改了蓝色
     *
     * @param str
     * @return
     */
    public static String changeHtmlColorTxt(String str) {
        String txt = "";
        if (!TextUtils.isEmpty(str)) {
            txt = str.replace("#3388ff", "rgb(51, 136, 255)");
        }
        return txt;
    }

    /**
     * 判断字符串是不是int格式防止NumberFormatException
     *
     * @return
     */
    public static boolean isInteger(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            if (pattern.matcher(s).matches()) {
                return true;
            } else {
                return false;
            }
        }
    }

    //设置tablayout宽度
    public static void setTabWidth(final TabLayout tabLayout, final int padding) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);


                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
