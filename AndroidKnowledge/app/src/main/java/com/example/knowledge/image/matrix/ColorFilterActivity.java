package com.example.knowledge.image.matrix;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.knowledge.R;

import java.util.ArrayList;
import java.util.List;

public class ColorFilterActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FiltersAdapter filtersAdapter;
    private List<float[]> filters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_filter);

        recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        inItFilters();
        filtersAdapter = new FiltersAdapter(Glide.with(this), getLayoutInflater(), filters);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(filtersAdapter);
    }

    private void inItFilters() {
        filters.add(ColorFilterUtil.colormatrix_heibai);
        filters.add(ColorFilterUtil.colormatrix_fugu);
        filters.add(ColorFilterUtil.colormatrix_gete);
        filters.add(ColorFilterUtil.colormatrix_chuan_tong);
        filters.add(ColorFilterUtil.colormatrix_danya);
        filters.add(ColorFilterUtil.colormatrix_guangyun);
        filters.add(ColorFilterUtil.colormatrix_fanse);
        filters.add(ColorFilterUtil.colormatrix_hepian);
        filters.add(ColorFilterUtil.colormatrix_huajiu);
        filters.add(ColorFilterUtil.colormatrix_jiao_pian);
        filters.add(ColorFilterUtil.colormatrix_landiao);
        filters.add(ColorFilterUtil.colormatrix_langman);
        filters.add(ColorFilterUtil.colormatrix_ruise);
        filters.add(ColorFilterUtil.colormatrix_menghuan);
        filters.add(ColorFilterUtil.colormatrix_qingning);
        filters.add(ColorFilterUtil.colormatrix_yese);
    }
}
