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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add("EncryptActivity（加解密）");

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

        itemAdapter.setOnItemClickLitener(new ItemAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View v) {
                int position = recyclerview.getChildAdapterPosition(v);
                if(position==0){
                    startActivity(new Intent(MainActivity.this, DecryptActivity.class));
                }
            }
        });
    }

}