package com.fifedu.lib_common_utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Uri2PathUtil {

    /**
     * 根据uri获取文件的绝对路径，兼容安卓10、11、12
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        String path = "";
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isDownloadsDocument(uri)) {
                String id = DocumentsContract.getDocumentId(uri);
                try {
                    Uri contentUri = null;
                    if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
                        final String[] split = id.split(":");
                        String strRealId = split[1];    //此时，strRealId = 7755
                        contentUri = ContentUris.withAppendedId(MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL),
                                Long.valueOf(strRealId));
                    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                        contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    }
                    path = getDataColumn(context, contentUri, null, null);
                } catch (Exception e) {
                    path = null;
                }
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                path = getDataColumn(context, contentUri, selection, selectionArgs);
            } else if (isExternalStorageDocument(uri)) {
                // 安卓11及以上，则不走这里逻辑，走下面的copy，因为这个目录下不能获取到真实的path
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
            }
        }
        // 处理安卓11及以上通过uri不能直接拿到下载、文档、手机存储目录下文件path，我们通过copy到私有目录的方式拿到
        if (TextUtils.isEmpty(path)) {
            try {
                DocumentFile documentFile = DocumentFile.fromSingleUri(context, uri);
                path = context.getExternalCacheDir().getAbsolutePath() + File.separator + documentFile.getName();
                Log.e("getDataColumn", "realPath:" + path);
                File file = new File(path);
                boolean isCopySuccess = copyFile(context, uri, file);

                Log.e("getDataColumn", "isCopySuccess:" + isCopySuccess);
            } catch (Exception e) {
                Log.e("getDataColumn", "copyFileToDir:" + e.getMessage());
            }
        }
        return path;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.MediaColumns.DATA;
        String[] projection = {column};
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String realPath = null;
        if (scheme == null)
            realPath = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            realPath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndexOrThrow(column);
                    if (index > -1) {
                        realPath = cursor.getString(index);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        }

        return realPath;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * 通过Uri copy 文件到path
     * 使用场景：除了IMAGES 、AUDIO、VIDEO这三个公共类型，系统不支持其它公共类型通过Uri获取文件filepPath，但可以通过复制这种方式获取复制后文件的filepPath，来替代原文件
     *
     * @param uri Uri string
     * @return boolean true or false
     */
    public static boolean copyFile(Context context, Uri uri, File file) {

        long startTime = System.currentTimeMillis();
        InputStream inputStream = null;
        OutputStream outStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            outStream = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int i = 0;
            // 将内容写到新文件当中
            while ((i = inputStream.read(bytes)) > 0) {
                outStream.write(bytes, 0, i);
            }

        } catch (Exception e) {
            Log.e("Uri2PathUtil", "copyFileToDir failure ,path = " + file.getAbsolutePath() + " ,msg = " + e.getMessage());
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                Log.e("Uri2PathUtil", "IOException: " + e.getMessage());
            }
        }
        // Now that we're finished, release the "pending" status, and allow other apps
        // to view the image.
        double l = Math.round(file.length() / (double) 1024 / 1024);
        //打印写入时间
        Log.i("Uri2PathUtil", "log_duration : " + (System.currentTimeMillis() - startTime)
                + " ,size : " + l + " M"
        );
        return true;
    }

}
