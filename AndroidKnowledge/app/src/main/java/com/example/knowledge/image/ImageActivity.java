package com.example.knowledge.image;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        findViewById(R.id.bt_base).setOnClickListener(this);
        findViewById(R.id.bt_distortion).setOnClickListener(this);
        findViewById(R.id.bt_color_matrix).setOnClickListener(this);
        findViewById(R.id.bt_color_hue).setOnClickListener(this);
        findViewById(R.id.bt_color_filter).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_base:
                intent.setClass(ImageActivity.this, MatrixBaseActivity.class);
                break;
            case R.id.bt_distortion:
                intent.setClass(ImageActivity.this, MatrixDistortionActivity.class);
                break;
            case R.id.bt_color_hue:
                intent.setClass(ImageActivity.this, ColorHueActivity.class);
                break;
            case R.id.bt_color_matrix:
                intent.setClass(ImageActivity.this, ColorMatrixActivity.class);
                break;
            case R.id.bt_color_filter:
                intent.setClass(ImageActivity.this, ColorFilterActivity.class);
                break;
        }
        ImageActivity.this.startActivity(intent);
    }
}
