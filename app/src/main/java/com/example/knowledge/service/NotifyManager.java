package com.example.knowledge.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.knowledge.R;
import com.example.knowledge.utils.LogUtil;

public class NotifyManager {

    public static Notification mNotification = null;
    private static NotificationChannel mChannel = null;
    private static android.app.NotificationManager mNotificationManager;
    private static final String NOTIFICATION_CHANNEL_ID = "1";
    public static final int NOTIFICATION_FOREGROUND_ID = 1;

    public static Notification getNotification(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (mNotificationManager == null) {
                mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            if (mChannel == null) {
                mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, context.getString(R.string.app_name), android.app.NotificationManager.IMPORTANCE_HIGH);
            }
            mNotificationManager.createNotificationChannel(mChannel);
            if (mNotification == null) {
                mNotification = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID).build();
            }
        }
        return mNotification;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void removeNotification() {
        try {
            if (mNotificationManager == null) {
                return;
            }
            mNotificationManager.cancel(NOTIFICATION_FOREGROUND_ID);
            //Android 10 java.lang.SecurityException:不允许删除前台服务的频道MyNotificationChannel
            mNotificationManager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID);
        } catch (Exception e) {
            LogUtil.d("NotifyManager", e.getMessage());
        }
    }
}
