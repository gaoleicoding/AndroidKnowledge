package com.example.knowledge;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge.adapter.ItemAdapter;
import com.example.knowledge.contentprovider.ProviderActivity;
import com.example.knowledge.decrypt.DecryptActivity;
import com.example.knowledge.design.CollapseActivity;
import com.example.knowledge.lambda.LambdaActivity;
import com.example.knowledge.ninepatch.NinePatchActivity;
import com.example.knowledge.recyclerview.RecyclerActivity;
import com.example.knowledge.view.StyleActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> list = new ArrayList<>();
    List<Class> activityList = new ArrayList<>();
    private int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add("SecondActivity（测试）");
        list.add("EncryptActivity（加解密）");
        list.add("CollapseActivity（折叠布局）");
        list.add("LambdaActivity（Lambda语法）");
        list.add("StyleActivity（Style使用）");
        list.add("ProviderActivity（ContentProvider使用）");
        list.add("NinePatchActivity（.9图片的使用）");
        list.add("RecyclerViewActivity（RecyclerView的使用）");
        activityList.add(SecondActivity.class);
        activityList.add(DecryptActivity.class);
        activityList.add(CollapseActivity.class);
        activityList.add(LambdaActivity.class);
        activityList.add(StyleActivity.class);
        activityList.add(ProviderActivity.class);
        activityList.add(NinePatchActivity.class);
        activityList.add(RecyclerActivity.class);

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
            startActivity(new Intent(MainActivity.this, activityList.get(position)));

        });
    }

}