package com.example.knowledge.activity;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

public class TestAndroidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title=findViewById(R.id.title_text);
        title.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        int mHeaderViewHeight = title.getHeight();
                        title.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }
}