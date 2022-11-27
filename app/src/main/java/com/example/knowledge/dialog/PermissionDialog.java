package com.example.knowledge.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * 申请权限的对话框
 * Created by baiqingtian on 2018/12/11.
 */

public class PermissionDialog {
    private static AlertDialog.Builder builder;
    private static Context mContext;

    /**
     * @param context
     * @param isExit  是否退出系统 （闪屏页点击退出退出应用）
     */
    public static void showPermissionDialog(final Context context, String permmision, final boolean isExit) {
        mContext = context;
        builder = new AlertDialog.Builder(mContext);
        builder.setTitle("权限提示");
        builder.setMessage(permmision + "权限尚未授权，将影响您的正常使用，请检查权限是否已经开启！");

        //退出
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isExit) {
                    System.exit(0);
                }
            }
        });
        //打开设置，让用户选择打开权限
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
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
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mContext.getPackageName());
        }
        mContext.startActivity(localIntent);
    }
}
