package com.example.knowledge.view.font;

import android.os.Bundle;
import android.widget.TextView;

import com.example.knowledge.BaseActivity;
import com.example.knowledge.R;
import com.example.knowledge.utils.TypeFaceUtil;

public class FontActivity extends BaseActivity {

    private TextView tvFont1, tvFont2, tvFont3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_font;
    }

    @Override
    public void initView() {
        tvFont1 = findViewById(R.id.tv_font1);
        tvFont2 = findViewById(R.id.tv_font2);
        tvFont3 = findViewById(R.id.tv_font3);
        TypeFaceUtil.setTypeface(tvFont1, TypeFaceUtil.fontPath_ARIAL);
        TypeFaceUtil.setTypeface(tvFont2, TypeFaceUtil.fontPath_DroidSansFallback);
        TypeFaceUtil.setTypeface(tvFont3, TypeFaceUtil.fontPath_DroidSansFallbackBd);
        tvHeader.setText("FontActivity");
    }

}
