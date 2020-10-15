package com.example.knowledge.optimize;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

/**
 * https://www.jianshu.com/p/8ad323834338
 */
public class LeakActivity extends AppCompatActivity {

    private final String TAG = "LeakActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

        // 跳转到新的LeakActivity里执行这段代码，然后退出这个LeakActivity。执行gc。
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 15000);
    }

}
