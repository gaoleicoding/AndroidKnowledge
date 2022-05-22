package com.example.knowledge.view.photo;

import android.net.Uri;

import androidx.core.content.FileProvider;

import com.example.knowledge.AppApplication;

import java.io.File;

public class FileProviderUtil {

    public static Uri getFileProviderUri(File file) {
        return FileProvider.getUriForFile(AppApplication.context, AppApplication.context.getPackageName() + ".fileProvider", file);
    }
}
