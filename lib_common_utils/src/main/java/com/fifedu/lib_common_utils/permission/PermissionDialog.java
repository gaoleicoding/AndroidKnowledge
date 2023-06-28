package com.fifedu.lib_common_utils.permission;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.fifedu.lib_common_utils.R;

/**
 * 申请权限的对话框
 * Created by baiqingtian on 2018/12/11.
 */

public class PermissionDialog {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    /**
     * @param isExit 是否退出系统 （闪屏页点击退出退出应用）
     */
    public static void showPermissionDialog(final Activity context, String permission, final boolean isExit) {
        mContext = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(context.getString(R.string.lib_utils_perm_request_title));

        String formatStr = context.getString(R.string.lib_utils_per_request_content);
        builder.setMessage(String.format(formatStr, permission));

        //退出
        builder.setNegativeButton(context.getString(R.string.lib_utils_exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isExit) {
                    context.finish();
                }
            }
        });
        //打开设置，让用户选择打开权限
        builder.setPositiveButton(context.getString(R.string.lib_utils_setting), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();//打开设置
            }
        });
        builder.setCancelable(false);
        if (mContext != null) {
            builder.show();
        }
    }

    /**
     * 打开系统应用设置(ACTION_APPLICATION_DETAILS_SETTINGS:系统设置权限)
     */
    private static void startAppSettings() {
        try {
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
            mContext.startActivity(localIntent);
        } catch (Exception e) {
        }
    }
}
