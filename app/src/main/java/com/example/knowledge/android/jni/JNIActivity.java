package com.example.knowledge.android.jni;

import android.view.View;

import com.example.knowledge.R;
import com.example.knowledge.activity.BaseActivity;
import com.example.knowledge.databinding.ActivityJniBinding;

public class JNIActivity extends BaseActivity {
    private final String TAG = "JNIActivity";

    @Override

    public int getLayoutId() {
        return R.layout.activity_jni;
    }

    @Override
    public void initView() {
        mTitleTv.setText("JNI");
        findViewById(R.id.bt_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initData() {

    }


}