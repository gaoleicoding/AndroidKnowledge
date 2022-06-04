package com.example.knowledge.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class MyServiceAssist extends Service {

    private final String TAG = "MyServiceAssist";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(NotifyManager.NOTIFICATION_FOREGROUND_ID, NotifyManager.getNotification(this));
        Log.d(TAG, "onCreate1");
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand1");
        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopForeground 停止服务并且取消通知栏 ，这种情况比较特殊，
        // 所以还要调用ServiceNotificationManager.removeNotification();
        stopForeground(true);
        NotifyManager.removeNotification();
        Log.d(TAG, "onDestroy1");
    }
}
