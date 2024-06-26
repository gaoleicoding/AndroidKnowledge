package com.fifedu.lib_common_utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaolei on 2023/5/27.
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 1.应用专属目录
     * 对应路径：/storage/emulated/0/Android/data/com.fifedu.kyxl/
     * 功能说明：应用被卸载后，与该应用相关的数据也清除掉
     * 对应清除选项：设置->应用->应用详情里面的”清除缓存“选项
     */
    private static final String photosExternalCacheDir = ContextProvider.getAppContext().getExternalCacheDir().getAbsolutePath() + File.separator + "photos/";
    private static final String videosExternalCacheDir = ContextProvider.getAppContext().getExternalCacheDir().getAbsolutePath() + File.separator + "videos/";
    private static final String audiosExternalCacheDir = ContextProvider.getAppContext().getExternalCacheDir().getAbsolutePath() + File.separator + "audios/";
    private static final String filesExternalCacheDir = ContextProvider.getAppContext().getExternalCacheDir().getAbsolutePath() + File.separator + "files/";

    /**
     * 2.共享目录
     * 公共目录包括：多媒体公共目录（Photos, Images, Videos, Audio）和下载文件目录（Downloads）。
     * APP可以通过MediaStore或者SAF（System Access Framework）的方式访问其中的文件。APP卸载后，文件不会被删除。
     */
    private static final String picPublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            + File.separator + ContextProvider.getAppContext().getPackageName();
    private static final String dcimPublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            + File.separator + ContextProvider.getAppContext().getPackageName();
    private static final String docPublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            + File.separator + ContextProvider.getAppContext().getPackageName();
    private static final String downloadPublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            + File.separator + ContextProvider.getAppContext().getPackageName();

    /**
     * 3.外部存储目录
     * 对应路径：/data/user/0/com.fifedu.kyxl/
     * 功能说明：应用被卸载后，这些数据还保留在SDCard中。一般存放需要长时间保存的数据，内存不足不会被清除
     * 对应清除选项：设置->应用->应用详情里面的”清除数据“选项
     */
    private static final String filesDir = ContextProvider.getAppContext().getFilesDir().getAbsolutePath();

    private static final String cacheDir = ContextProvider.getAppContext().getCacheDir().getAbsolutePath();
    private static final String photosCacheDir = ContextProvider.getAppContext().getCacheDir().getAbsolutePath() + File.separator + "photos";

    public static String getPicPublicDirectory() {
        makeFileDirs(picPublicDirectory);
        return picPublicDirectory;
    }

    public static String getDCIMPublicDirectory() {
        makeFileDirs(dcimPublicDirectory);
        return dcimPublicDirectory;
    }

    public static String getDocPublicDirectory() {
        makeFileDirs(docPublicDirectory);
        return docPublicDirectory;
    }

    public static String getDownloadPublicDirectory() {
        makeFileDirs(downloadPublicDirectory);
        return downloadPublicDirectory;
    }

    public static String getExternalFileDir(String type) {
        String path = ContextProvider.getAppContext().getExternalFilesDir(type).getAbsolutePath();
        makeFileDirs(path);
        return path;
    }

    public static String getPhotosExternalCacheDir() {
        makeFileDirs(photosExternalCacheDir);
        return photosExternalCacheDir;
    }

    public static String getVideosExternalCacheDir() {
        makeFileDirs(videosExternalCacheDir);
        return videosExternalCacheDir;
    }

    public static String getAudiosExternalCacheDir() {
        makeFileDirs(audiosExternalCacheDir);
        return audiosExternalCacheDir;
    }

    public static String getFilesExternalCacheDir() {
        makeFileDirs(filesExternalCacheDir);
        return filesExternalCacheDir;
    }

    public static String getCacheDir() {
        makeFileDirs(cacheDir);
        return cacheDir;
    }

    public static String getPhotosCacheDir() {
        makeFileDirs(photosCacheDir);
        return photosCacheDir;
    }

    public static String getFilesDir() {
        makeFileDirs(filesDir);
        return filesDir;
    }

    private static void makeFileDirs(String path) {
        File dir = new File(path);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
    }

    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    /**
     * 获取指定文件大小
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (IOException e) {

            }
        }
        return size;
    }

    /**
     * 获取文件后缀名
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }


    /**
     * 保留文件名及后缀
     */
    public static String getRealFileName(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        int start = path.lastIndexOf("/");
        if (start != -1) {
            return path.substring(start + 1);
        } else {
            return "";
        }
    }

    public static String getUploadFileName(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        int start = path.lastIndexOf("/");
        if (start != -1) {
            String realName = path.substring(start + 1);
            int realNameLength = realName.length();
            if (realNameLength >= 50) {
                //避免报错，如果文件名长度大于50个字符，则截除49个字符
                String extensionName = "";
                int extensionNameLength = 0;
                int dot = realName.lastIndexOf(".");
                if ((dot > -1) && (dot < (realNameLength - 1))) {
                    extensionName = realName.substring(dot);
                    extensionNameLength = extensionName.length();
                    return realName.substring(0, (49 - extensionNameLength)) + extensionName;
                } else {
                    return realName.substring(0, (49 - extensionNameLength));
                }

            } else {
                return realName;
            }
        } else {
            return "";
        }
    }

    public static String getFileNameByCurrentDate(File file) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date) + "." + getExtend(file, "jpg");
    }

    public static String getExtend(File file, String defExt) {
        String filename = file.getName();
        if (filename.length() > 0) {
            int i = filename.lastIndexOf('.');

            if ((i > 0) && (i < (filename.length() - 1))) {
                return (filename.substring(i + 1)).toLowerCase();
            }
        }
        return defExt.toLowerCase();
    }


    /**
     * 仅保留文件名不保留后缀
     */
    public static String getFileName(String path) {
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return path.substring(start + 1, end);
        } else {
            return null;
        }
    }

    /**
     * 保留文件名及后缀
     */
    public static String getFileNameWithSuffix(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        int start = path.lastIndexOf("/");
        if (start != -1) {
            return path.substring(start + 1);
        } else {
            return null;
        }
    }

    /**
     * 删除目录下所有文件
     */
    public static void deleteAllFiles(File root) {
        try {
            if (root != null && root.exists()) {
                File[] files = root.listFiles();
                if (files != null)
                    for (File f : files) {
                        if (f.isDirectory()) { // 判断是否为文件夹
                            deleteAllFiles(f);
                            try {
                                f.delete();
                            } catch (Exception ignored) {
                            }
                        } else {
                            if (f.exists()) { // 判断是否存在
                                deleteAllFiles(f);
                                try {
                                    f.delete();
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 将字符串存入文件
     */
    public static void putString(String str, String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 把数据写到文件中
        OutputStreamWriter outStream = null;
        try {
            FileOutputStream out = new FileOutputStream(file);
            outStream = new OutputStreamWriter(out, "utf-8");
            BufferedWriter writer = new BufferedWriter(outStream);
            writer.write(str);
            writer.close();
            out.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在文件中追加写内容
     *
     * @param string   内容
     * @param filePath 文件路径
     */
    public static void appendWriteFileToSdcard(FragmentActivity fragmentActivity, final String string, final String filePath) {


        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath, true);
            writer.write(string);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String path) {
        String str = "";
        File file = new File(path);
        if (file.exists()) {
            try {
                InputStreamReader input = new InputStreamReader(new FileInputStream(file));
                BufferedReader reader = new BufferedReader(input);
                StringBuilder sb = new StringBuilder();
                String empString = null;
                while ((empString = reader.readLine()) != null) {
                    str = new String(sb.append(empString).toString().getBytes(), "utf-8");
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    //去掉扩展名
    public static String getFileNameWithoutExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 重命名文件
     *
     * @param oldPath 原来的文件地址
     * @param newPath 新的文件地址
     */
    public static void renameFile(String oldPath, String newPath) {

        if (TextUtils.isEmpty(oldPath)) {
            return;
        }

        if (TextUtils.isEmpty(newPath)) {
            return;
        }

        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
    }

    public static void copyFile(File src, File dst) throws IOException {

        FileInputStream in;
        in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();

    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {

        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();

    }

    // 获取文件 md5
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /*
     * android9之前，扫描图片使用MediaScannerConnection
     * android10之后，则需要把文件复制到公共目录下，如DCIM
     * 虽然android10的方法可以向下兼容，但复制文件效率始终不如刷新媒体库，最好是根据SDK_INT选择方法
     */
    public static void scanMedia(Context context, File file) {
        if (file == null || !file.isFile()) {
            return;
        }
        String mimeType = getMimeType(context, file);
        String filePath = file.getAbsolutePath();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String fileName = file.getName();
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) {
                return;
            }
            try {
                OutputStream out = contentResolver.openOutputStream(uri);
                FileInputStream fis = new FileInputStream(file);
                FileUtils.copyFile(fis, out);
                fis.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //以前老版本方法通过广播刷新方法在API29中已经废弃了无法使用
            //这个是官方提供了新版本方法
            MediaScannerConnection.scanFile(context, new String[]{filePath}, new String[]{mimeType}, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    //刷新成功的回调方法
                }
            });
        }
    }


    public static String getMimeType(Context context, File file) {
        Uri uri = getFileProviderUri(file);
        ContentResolver contentResolver = context.getContentResolver();
        return contentResolver.getType(uri);
    }

    public static Uri getFileUri(File file) {
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = getFileProviderUri(file);
        } else {
            imageUri = Uri.fromFile(file);
        }
        return imageUri;
    }

    public static Uri getFileUri(String provider, File file) {
        return getFileUri(ContextProvider.getAppContext(), provider, file);
    }

    public static Uri getFileUri(Context context, String provider, File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, provider, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static Uri getIntentFileUri(Intent cameraIntent, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //这一句非常重要
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        return getFileUri(file);
    }


    public static Uri getFileProviderUri(File file) {
        return FileProvider.getUriForFile(ContextProvider.getAppContext(), AppUtil.getAppProvider(ContextProvider.getAppContext(), "fileProvider"), file);
    }
}
