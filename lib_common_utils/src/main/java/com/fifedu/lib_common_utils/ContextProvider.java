package com.fifedu.lib_common_utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by gaolei on 2023/5/27.
 */
public class ContextProvider extends ContentProvider {

    private static final String TAG = "ContextProvider";

    /**
     * 通过ContentProvider获取ApplicationContext
     */
    private static Context context;

    public static Context getAppContext() {
        return context;
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(context, config);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

