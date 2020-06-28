package com.example.knowledge;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge.adapter.ItemAdapter;
import com.example.knowledge.asynctask.AsyncActivity;
import com.example.knowledge.contentprovider.ProviderActivity;
import com.example.knowledge.decrypt.DecryptActivity;
import com.example.knowledge.design.CollapseActivity;
import com.example.knowledge.lambda.LambdaActivity;
import com.example.knowledge.ninepatch.NinePatchActivity;
import com.example.knowledge.recyclerview.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity {


    String[] items = {
            "SecondActivity（测试)",
            "EncryptActivity（加解密）",
            "CollapseActivity（折叠布局）",
            "LambdaActivity（Lambda语法）",
            "ProviderActivity（ContentProvider使用）",
            "NinePatchActivity（.9图片的使用）",
            "RecyclerViewActivity（RecyclerView的使用）",
            "AsyncActivity（AsyncTask的使用）"
    };
    Class[] activities = {SecondActivity.class, DecryptActivity.class, CollapseActivity.class, LambdaActivity.class,
            ProviderActivity.class, NinePatchActivity.class, RecyclerViewActivity.class, AsyncActivity.class};
    private int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        ItemAdapter itemAdapter = new ItemAdapter(this, items);
        recyclerview.setAdapter(itemAdapter);
        recyclerview.addItemDecoration(
                new DividerItemDecoration(MainActivity.this,
                        DividerItemDecoration.VERTICAL
                )
        );

        itemAdapter.setOnItemClickLitener(v -> {
            int position = recyclerview.getChildAdapterPosition(v);
            startActivity(new Intent(MainActivity.this, activities[position]));

        });
    }

}