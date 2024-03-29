package com.example.knowledge.image;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.image.beautify.SlimFaceActivity;
import com.example.knowledge.image.matrix.ColorFilterActivity;
import com.example.knowledge.image.matrix.ColorHueActivity;
import com.example.knowledge.image.matrix.ColorMatrixActivity;
import com.example.knowledge.image.matrix.MatrixBaseActivity;
import com.example.knowledge.image.matrix.MatrixDistortionActivity;

public class ImgProcessActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        findViewById(R.id.bt_base).setOnClickListener(this);
        findViewById(R.id.bt_distortion).setOnClickListener(this);
        findViewById(R.id.bt_color_matrix).setOnClickListener(this);
        findViewById(R.id.bt_color_hue).setOnClickListener(this);
        findViewById(R.id.bt_color_filter).setOnClickListener(this);
        findViewById(R.id.bt_slim_face).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_base:
                intent.setClass(ImgProcessActivity.this, MatrixBaseActivity.class);
                break;
            case R.id.bt_distortion:
                intent.setClass(ImgProcessActivity.this, MatrixDistortionActivity.class);
                break;
            case R.id.bt_color_hue:
                intent.setClass(ImgProcessActivity.this, ColorHueActivity.class);
                break;
            case R.id.bt_color_matrix:
                intent.setClass(ImgProcessActivity.this, ColorMatrixActivity.class);
                break;
            case R.id.bt_color_filter:
                intent.setClass(ImgProcessActivity.this, ColorFilterActivity.class);
                break;
            case R.id.bt_slim_face:
                intent.setClass(ImgProcessActivity.this, SlimFaceActivity.class);
                break;

        }
        ImgProcessActivity.this.startActivity(intent);
    }
}
