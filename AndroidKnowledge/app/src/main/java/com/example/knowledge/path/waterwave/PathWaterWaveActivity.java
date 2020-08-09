package com.example.knowledge.path.waterwave;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.knowledge.R;

public class PathWaterWaveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_wave);
        WaterWaveProgress waterWave = findViewById(R.id.waterWave);
        waterWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterWave.animateWave();
            }
        });
    }

}