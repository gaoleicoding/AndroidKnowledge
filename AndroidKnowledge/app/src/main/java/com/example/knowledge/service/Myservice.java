package com.example.knowledge.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class Myservice extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Myservice=======", "onCreate");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(ServiceNotificationManager.NOTIFICATION_FOREGROUND_ID, ServiceNotificationManager.getNotification(this));
            Intent intent = new Intent(this, Myservice1.class);
            startForegroundService(intent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Myservice=======", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Myservice=======", "onDestroy");
    }
}
