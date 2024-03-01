package com.fifedu.lib_common_utils.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.fifedu.lib_common_utils.ContextProvider;
import com.fifedu.lib_common_utils.FileUtils;
import com.fifedu.lib_common_utils.R;
import com.fifedu.lib_common_utils.ToastUtil;
import com.fifedu.lib_common_utils.log.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by gaolei on 2023/5/27.
 */
public class GlideUtil {

    public static void loadImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    /**
     * load 网络资源load(url)
     * load SD卡资源：load("file://"+Environment.getExternalStorageDirectory().getPath()+"/test.jpg")
     * load ContentProvider资源：load("content://media/external/images/media/139469")
     * load assets资源：load("file:///android_asset/f003.gif")
     */
    public static void loadImg(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    public static void loadImgWithDefault(ImageView imageView, String url, int defaultRes) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(defaultRes)
                .into(imageView);
    }

    public static void loadGIF(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .asGif()
                .load(url)
                .into(imageView);
    }

    public static void loadResGIF(ImageView imageView, int resId) {
        Glide.with(imageView.getContext())
                .asGif()
                .load(resId)
                .into(imageView);
    }

    //Glide设置图片圆角角度和默认
    public static void loadImgWithCornerDefault(ImageView imageView, String url, int radius, int defaultRes) {
        RoundedCorners roundedCorners = new RoundedCorners(radius);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .thumbnail(loadTransform(imageView.getContext(), defaultRes, radius))
                .placeholder(defaultRes)
                .into(imageView);
    }

    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId, int radius) {

        return Glide.with(context)
                .load(placeholderId)
                .apply(new RequestOptions().centerCrop()
                        .transform(new RoundedCorners(radius)));

    }

    //Glide设置图片圆角角度
    public static void loadImgWithCorner(ImageView imageView, String url, int corner) {
        RoundedCorners roundedCorners = new RoundedCorners(corner);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadResImgWithCorner(ImageView imageView, int resId, int corner) {
        RoundedCorners roundedCorners = new RoundedCorners(corner);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(imageView.getContext())
                .load(resId)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void showCircleImgFromNet(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .circleCrop()
                .into(imageView);
    }

    public static void showCircleImgFromRes(int resId, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(resId)
                .circleCrop()
                .into(imageView);
    }

    /**
     * 加载圆形图片(带默认图和边框)
     */
    public static void loadImgCircleWithBorder(String url, ImageView imageView, int borderWidth, int borderColor) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new GlideCircleWithBorder(borderWidth, borderColor));
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);
    }

    public static void loadResImgCircleWithBorder(ImageView imageView, int resId, int borderWidth, int borderColor) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new GlideCircleWithBorder(borderWidth, borderColor));
        Glide.with(imageView.getContext()).load(resId).apply(options).into(imageView);
    }

    /**
     * 设置模糊度模糊度(在0.0到25.0之间)，25f是最大模糊度
     */
    public static void loadImgWithBlur(ImageView imageView, String url, int radius) {
        Glide.with(imageView.getContext())
                .load(url)
                .transform(new BlurTransformation(ContextProvider.getAppContext(), radius))
                .into(imageView);
    }

    public static void loadResImgWithBlur(ImageView imageView, int resId, int radius) {
        Glide.with(imageView.getContext())
                .load(resId)
                // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .transform(new BlurTransformation(ContextProvider.getAppContext(), radius))
                .into(imageView);
    }

    public static void loadImgToLocal(Context context, String url) {
        //Glide的下载文件必须在后台线程中调用，默认是主线程
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                    saveToAlbum(context, file);
                    ToastUtil.showToast(context.getString(R.string.lib_utils_img_save_sucess));
                } catch (Exception e) {
                    LogUtils.e("GlideUtil", e.getMessage());
                    ToastUtil.showToast(context.getString(R.string.lib_utils_img_save_failure));
                }
            }
        }.start();

    }

    /**
     * 1.复制图片
     * 2.扫描文件
     */
    private static void saveToAlbum(Context context, File srcFile) throws IOException {
        String destPath = FileUtils.getPhotosExternalCacheDir();
        File dstFile = new File(destPath, "IMG_" + System.currentTimeMillis() + ".png");
        // Glide下载的文件名是处理过的，如6eada2b637245f067db448b913a7a5667c51326bf2f3d7c635d04ffbc0616a57.0，所以我们需要保存为正常文件名的图片
        FileUtils.copyFile(srcFile, dstFile);
        FileUtils.scanMedia(context, dstFile);
    }


}
