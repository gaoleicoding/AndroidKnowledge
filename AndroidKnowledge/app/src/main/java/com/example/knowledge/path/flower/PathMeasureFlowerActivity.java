package com.example.knowledge.path.flower;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;


public class PathMeasureFlowerActivity extends AppCompatActivity {

    private FlowerView fllower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fllower);

        fllower = (FlowerView) findViewById(R.id.fllower);
    }

    public void show(View view) {
        fllower.startAnimation();
    }

}
