package com.fifedu.lib_common_utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by gaolei on 2023/5/27.
 */
public class BitmapUtil {
    public static final int maxWidth = 720;
    public static final int maxHeight = 1080;

    /**
     * 检查文件是否损坏
     * Check if the file is corrupted
     *
     * @return false为不损坏，true为损坏
     */
    public static boolean checkImgCorrupted(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return (options.mCancel || options.outWidth == -1 || options.outHeight == -1);
    }

    public static int[] getImageSize(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    //读取图片旋转角度
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                degree = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                degree = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                degree = 270;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 校正图片旋转角度,并压缩图片质量
     */
    public static File checkPictureDegree(File file, boolean isCompress) {
        int degree = 0;
        String path = file.getAbsolutePath();
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                degree = 90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                degree = 180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                degree = 270;
            }
            if (degree == 0 && !isCompress) return file;

            Bitmap tempBitmap = compressImageFromFile(path, isCompress);
            if (degree != 0) {
                tempBitmap = rotateBitmap(tempBitmap, degree);
            }
            return bitmap2File(tempBitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 对图片进行压缩
     *
     * @param srcPath
     * @return
     */
    public static Bitmap compressImageFromFile(String srcPath, boolean isCompress) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只读边,不读内容
        BitmapFactory.decodeFile(srcPath, options);

        int w = options.outWidth;
        int h = options.outHeight;

        options.inSampleSize = isCompress ? calcuteInSampleSize(w, h, BitmapUtil.maxWidth, BitmapUtil.maxHeight) : 1;//设置采样率
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;//默认Bitmap.Config.ARGB_8888

        return BitmapFactory.decodeFile(srcPath, options);
    }

    public static int calcuteInSampleSize(int w, int h, int maxW, int maxH) {
        int inSampleSize = 1;

        if (w > maxW && h > maxH) {
            inSampleSize = 2;
            while ((w / inSampleSize > maxW) && (h / inSampleSize > maxH)) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static File bitmap2File(Bitmap bitmap) {
        String picRootDirectory = FileUtils.getPhotosExternalCacheDir();
        File dir = new File(picRootDirectory);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        final String imgName = "IMG_" + System.currentTimeMillis() + ".jpg";
        final File file = new File(picRootDirectory, imgName);

        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @NonNull
    public static String byteToFile(final byte[] data) {
        //拍照后的处理
        String picRootDirectory = FileUtils.getPhotosExternalCacheDir();
        File dir = new File(picRootDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String path = dir + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg";
        File file = new File(path);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    // Ignore
                    e.printStackTrace();
                }
            }
        }
        return path;
    }

    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static String getImgBase64(Bitmap bitmap) {
        if (bitmap == null) return "";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bb = bos.toByteArray();

        return Base64.encodeToString(bb, Base64.NO_WRAP);
    }


    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    // 保存并刷新图库显示
    public static String saveAndRefresh(Context context, Bitmap bitmap) {
        String cacheDirectory = Environment.getExternalStorageDirectory().getPath();
        long l = System.currentTimeMillis();
        String filePath = cacheDirectory + l + ".png";

        File imagePath = new File(filePath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
        } finally {
            try {
                fos.close();
                //最后通知图库更新
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);//扫描单个文件
                intent.setData(Uri.fromFile(imagePath));//给图片的绝对路径
                context.sendBroadcast(intent);
            } catch (Exception e) {
            }
        }

        return filePath;
    }

    /**
     * Drawable 转 Bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        //声明将要创建的bitmap
        Bitmap bitmap = null;
        //获取图片宽度
        int width = drawable.getIntrinsicWidth();
        //获取图片高度
        int height = drawable.getIntrinsicHeight();
        //图片位深，PixelFormat.OPAQUE代表没有透明度，RGB_565就是没有透明度的位深，否则就用ARGB_8888。详细见下面图片编码知识。
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        //创建一个空的Bitmap
        bitmap = Bitmap.createBitmap(width, height, config);
        //在bitmap上创建一个画布
        Canvas canvas = new Canvas(bitmap);
        //设置画布的范围
        drawable.setBounds(0, 0, width, height);
        //将drawable绘制在canvas上
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 图片毛玻璃效果
     *
     * @param context
     * @param sentBitmap
     * @param radius     (0-25表示模糊值)
     * @return
     */
    @SuppressLint("NewApi")
    public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {
        if (sentBitmap.getConfig() != null) {
            if (Build.VERSION.SDK_INT > 17) {

                Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

                final RenderScript rs = RenderScript.create(context);
                final Allocation input = Allocation.createFromBitmap(rs,
                        sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
                        Allocation.USAGE_SCRIPT);
                final Allocation output = Allocation.createTyped(rs,
                        input.getType());
                final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(
                        rs, Element.U8_4(rs));
                script.setRadius(radius);
                script.setInput(input);
                script.forEach(output);
                output.copyTo(bitmap);
                return bitmap;
            }

            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

            if (radius < 1) {
                return (null);
            }

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            int[] pix = new int[w * h];
            bitmap.getPixels(pix, 0, w, 0, 0, w, h);

            int wm = w - 1;
            int hm = h - 1;
            int wh = w * h;
            int div = radius + radius + 1;

            int r[] = new int[wh];
            int g[] = new int[wh];
            int b[] = new int[wh];
            int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
            int vmin[] = new int[Math.max(w, h)];

            int divsum = (div + 1) >> 1;
            divsum *= divsum;
            int temp = 256 * divsum;
            int dv[] = new int[temp];
            for (i = 0; i < temp; i++) {
                dv[i] = (i / divsum);
            }

            yw = yi = 0;

            int[][] stack = new int[div][3];
            int stackpointer;
            int stackstart;
            int[] sir;
            int rbs;
            int r1 = radius + 1;
            int routsum, goutsum, boutsum;
            int rinsum, ginsum, binsum;

            for (y = 0; y < h; y++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                for (i = -radius; i <= radius; i++) {
                    p = pix[yi + Math.min(wm, Math.max(i, 0))];
                    sir = stack[i + radius];
                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);
                    rbs = r1 - Math.abs(i);
                    rsum += sir[0] * rbs;
                    gsum += sir[1] * rbs;
                    bsum += sir[2] * rbs;
                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }
                }
                stackpointer = radius;

                for (x = 0; x < w; x++) {

                    r[yi] = dv[rsum];
                    g[yi] = dv[gsum];
                    b[yi] = dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (y == 0) {
                        vmin[x] = Math.min(x + radius + 1, wm);
                    }
                    p = pix[yw + vmin[x]];

                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[(stackpointer) % div];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi++;
                }
                yw += w;
            }
            for (x = 0; x < w; x++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                yp = -radius * w;
                for (i = -radius; i <= radius; i++) {
                    yi = Math.max(0, yp) + x;

                    sir = stack[i + radius];

                    sir[0] = r[yi];
                    sir[1] = g[yi];
                    sir[2] = b[yi];

                    rbs = r1 - Math.abs(i);

                    rsum += r[yi] * rbs;
                    gsum += g[yi] * rbs;
                    bsum += b[yi] * rbs;

                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }

                    if (i < hm) {
                        yp += w;
                    }
                }
                yi = x;
                stackpointer = radius;
                for (y = 0; y < h; y++) {
                    pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
                            | (dv[gsum] << 8) | dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (x == 0) {
                        vmin[y] = Math.min(y + r1, hm) * w;
                    }
                    p = x + vmin[y];

                    sir[0] = r[p];
                    sir[1] = g[p];
                    sir[2] = b[p];

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[stackpointer];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi += w;
                }
            }

            bitmap.setPixels(pix, 0, w, 0, 0, w, h);
            return (bitmap);
        } else {
            return null;
        }
    }

}
