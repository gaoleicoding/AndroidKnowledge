package com.example.knowledge.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.view.image.ImageActivity;
import com.example.knowledge.view.photo.PhotoSelectActivity;
import com.example.knowledge.view.webview.WebViewActivity;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        findViewById(R.id.bt_webview).setOnClickListener(this);
        findViewById(R.id.bt_image).setOnClickListener(this);
        findViewById(R.id.bt_photo_select).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_webview:
                intent.setClass(ViewActivity.this, WebViewActivity.class);
                break;
            case R.id.bt_image:
                intent.setClass(ViewActivity.this, ImageActivity.class);
                break;
            case R.id.bt_photo_select:
                intent.setClass(ViewActivity.this, PhotoSelectActivity.class);
                break;


        }
        ViewActivity.this.startActivity(intent);
    }
}
