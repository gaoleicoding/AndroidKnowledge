package com.example.knowledge.coordinatorlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.knowledge.BaseActivity;
import com.example.knowledge.R;

public class CollapseActivity extends BaseActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse);


        collapsingToolbarLayout = findViewById(R.id.main3_CollapsingToolbarLayout);
        toolbar = findViewById(R.id.main3_toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标
        //左上角返回箭头的监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingToolbarLayout.setTitle("首页");
        //设置收缩前字体大小,这个style是自己写的
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TitleTextSize);
        //设置收缩后字体大小 collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.TitleTextSize);
        //设置收缩后Toolbar上字体的颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.RED);

    }

}