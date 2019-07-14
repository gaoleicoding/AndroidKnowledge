package com.example.knowledge.inflate;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.knowledge.R;

public class TitleView extends FrameLayout {


    private TextView titleText;

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
       View view= LayoutInflater.from(context).inflate(R.layout.title, this,true);
        titleText = findViewById(R.id.title_text);
        addView(view);
    }

    public void setTitleText(String text) {
        titleText.setText(text);
    }



}