package com.example.knowledge.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.knowledge.R;

public class ServiceNotificationManager {

    public static Notification mNotification = null;
    private static NotificationChannel mChannel = null;
    private static NotificationManager mNotificationManager;
    private static final String NOTIFICATION_CHANNEL_ID = "1";
    public static final int NOTIFICATION_FOREGROUND_ID = 1;

    public static Notification getNotification(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (mNotificationManager == null) {
                mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            if (mChannel == null) {
                mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, context.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
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
        if (mNotificationManager == null) {
            return;
        }
        mNotificationManager.cancel(NOTIFICATION_FOREGROUND_ID);
        mNotificationManager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID);
    }
}
