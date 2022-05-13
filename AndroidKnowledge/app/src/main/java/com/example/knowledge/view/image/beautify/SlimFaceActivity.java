package com.example.knowledge.view.image.beautify;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

public class SlimFaceActivity extends AppCompatActivity implements View.OnClickListener {

    private SlimFaceView myPSView;
    private int screenWidth, screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slim_face);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();

        final TextView btn_reset = (TextView) findViewById(R.id.btn_reset);
        myPSView = (SlimFaceView) findViewById(R.id.myPSView);
        btn_reset.setOnClickListener(this);
        myPSView.setScreenSize(screenWidth, screenHeight);
        myPSView.setOnStepChangeListener(isEmpty -> {
            btn_reset.setTextColor(isEmpty ? Color.parseColor("#999999") : Color.parseColor("#000000"));
            btn_reset.setEnabled(!isEmpty);
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset:
                myPSView.resetView();
                break;
        }
    }
}
