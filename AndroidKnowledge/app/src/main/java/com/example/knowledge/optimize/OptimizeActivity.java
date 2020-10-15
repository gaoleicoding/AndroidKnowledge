package com.example.knowledge.optimize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

public class OptimizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);

    }

    public void toLeakActivity(View view) {
        startActivity(new Intent(this, LeakActivity.class));
    }

    public void toBlockActivity(View view) {
        startActivity(new Intent(this, BlockActivity.class));
    }


}
