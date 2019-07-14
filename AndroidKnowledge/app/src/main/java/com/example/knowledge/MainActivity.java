package com.example.knowledge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.knowledge.inflate.DemoAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView article_recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        article_recyclerview=findViewById(R.id.article_recyclerview);
        initRecyclerView();
//        //父容器
//        LinearLayout parent = findViewById(R.id.ll_root);
//        //获取布局填充器
//        LayoutInflater inflater = LayoutInflater.from(this);
//        //获取子布局，并动态加载进父容器中
//        View child = inflater.inflate(R.layout.title, parent, false);
//        parent.addView(child);



    }

    private void initRecyclerView() {
        List<String> dataList = new ArrayList<>();
        for(int i=0;i<10;i++){
            dataList.add("第"+i+"项");
        }
        DemoAdapter adapter = new DemoAdapter(this, dataList);

        article_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        article_recyclerview.setAdapter(adapter);

        }
}