package com.example.knowledge.android.font;

import android.widget.TextView;

import com.example.knowledge.R;
import com.example.knowledge.activity.BaseActivity;
import com.example.knowledge.utils.TypeFaceUtil;

public class FontActivity extends BaseActivity {

    private TextView tvFont1, tvFont2, tvFont3;

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
        mTitleTv.setText("Font");
    }

    @Override
    public void initData() {

    }
}
