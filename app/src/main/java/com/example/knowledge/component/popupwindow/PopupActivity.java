package com.example.knowledge.component.popupwindow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.knowledge.R;

public class PopupActivity extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        findViewById(R.id.rl_more).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_more) {
            PopWindow popWindow = new PopWindow(this);
            popWindow.showPopupWindow(findViewById(R.id.rl_more));
        }
    }
}