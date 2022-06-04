package com.example.knowledge.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class MyService extends Service {
    private final String TAG = "MyService";
    private int count;
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            count += 1;
            Message msg2 = Message.obtain();
            handler.sendMessageDelayed(msg2, 1000);
            Log.d(TAG, "handleMessge-count: " + count);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NotifyManager.NOTIFICATION_FOREGROUND_ID, NotifyManager.getNotification(this));
            Intent intent = new Intent(this, MyServiceAssist.class);
            startForegroundService(intent);
        }
        Message msg = Message.obtain();
        handler.sendMessageDelayed(msg, 1000);
        acquireWakeLock();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        handler.removeCallbacksAndMessages(null);
        releaseWakeLock();
    }

    WakeLock mWakeLock = null;

    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行，当TimerTask开始运行时加入如下方法
    private void acquireWakeLock() {
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GL::ProcessAliveActivity");
            if (null != mWakeLock) {
                mWakeLock.acquire();
            }
        }
    }

    //释放电源锁
    public void releaseWakeLock() {
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }
}
