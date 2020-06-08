package com.example.knowledge.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

import java.util.concurrent.Executor;

public class AsyncActivity extends AppCompatActivity {

    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        Executor executor=AsyncTask.SERIAL_EXECUTOR;
        DownloadImgTask task = new DownloadImgTask(this);
        task.executeOnExecutor(executor, "testurl1");
        DownloadImgTask task2 = new DownloadImgTask(this);
        task2.executeOnExecutor(executor, "testurl2");
        DownloadImgTask task3 = new DownloadImgTask(this);
        task3.executeOnExecutor(executor, "testurl3");

//        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "AsyncTask-currentThread:" + Thread.currentThread().getName());
//            }
//        });
    }
}
