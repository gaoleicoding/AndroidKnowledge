package com.example.knowledge.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.image.beautify.SlimFaceActivity;
import com.example.knowledge.image.matrix.ColorFilterActivity;
import com.example.knowledge.image.matrix.ColorHueActivity;
import com.example.knowledge.image.matrix.ColorMatrixActivity;
import com.example.knowledge.image.matrix.MatrixBaseActivity;
import com.example.knowledge.image.matrix.MatrixDistortionActivity;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        findViewById(R.id.bt_webview).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.bt_webview:
                intent.setClass(ViewActivity.this, WebViewActivity.class);
                break;


        }
        ViewActivity.this.startActivity(intent);
    }
}
