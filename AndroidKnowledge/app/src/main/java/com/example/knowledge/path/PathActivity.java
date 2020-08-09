package com.example.knowledge.path;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.path.flower.PathMeasureFlowerActivity;
import com.example.knowledge.path.status.PathMeasureStatusActivity;
import com.example.knowledge.path.waterwave.PathWaterWaveActivity;

public class PathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
    }

    public void toPathFlowerActivity(View view) {
        startActivity(new Intent(this, PathMeasureFlowerActivity.class));
    }

    public void toPathStatusActivity(View view) {
        startActivity(new Intent(this, PathMeasureStatusActivity.class));
    }

    public void toWaterWaveActivity(View view) {
        startActivity(new Intent(this, PathWaterWaveActivity.class));
    }

}
