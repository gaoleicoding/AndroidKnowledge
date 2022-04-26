package com.example.knowledge.process;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.example.knowledge.R;

public class ProcessAliveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_alive);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new MyLifecycleObserver());

    }
    private class MyLifecycleObserver implements LifecycleObserver {

        // 方法名随便取，注解才是重点
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onForeground() {
            Log.i("LifecycleObserver", "应用回到前台");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onBackground() {
            Log.i("LifecycleObserver", "应用退到后台");
        }
    }
}