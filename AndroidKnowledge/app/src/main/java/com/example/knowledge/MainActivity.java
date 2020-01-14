package com.example.knowledge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge.adapter.ItemAdapter;
import com.example.knowledge.decrypt.DecryptActivity;
import com.example.knowledge.design.CollapseActivity;
import com.example.knowledge.lambda.LambdaActivity;
import com.example.knowledge.view.StyleActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add("EncryptActivity（加解密）");
        list.add("CollapseActivity（折叠布局）");
        list.add("LambdaActivity（Lambda语法）");
        list.add("StyleActivity（Style使用）");

        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(this, list);
        recyclerview.setAdapter(itemAdapter);
        recyclerview.addItemDecoration(
                new DividerItemDecoration(MainActivity.this,
                        DividerItemDecoration.VERTICAL
                )
        );

        itemAdapter.setOnItemClickLitener(v -> {
            int position = recyclerview.getChildAdapterPosition(v);
            if (position == 0) {
                startActivity(new Intent(MainActivity.this, DecryptActivity.class));
            }
            if (position == 1) {
                startActivity(new Intent(MainActivity.this, CollapseActivity.class));
            }
            if (position == 2) {
                startActivity(new Intent(MainActivity.this, LambdaActivity.class));
            }
            if (position == 3) {
                startActivity(new Intent(MainActivity.this, StyleActivity.class));
            }
        });
    }

}