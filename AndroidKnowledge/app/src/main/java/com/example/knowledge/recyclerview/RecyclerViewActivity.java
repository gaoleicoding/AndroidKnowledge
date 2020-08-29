package com.example.knowledge.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.itemdecoration.RVItemDecorationActivity;
import com.example.knowledge.recyclerview.layoutmanager.LayoutManagerActivity;
import com.example.knowledge.recyclerview.layoutmanager.card.CardLayoutManagerActivity;
import com.example.knowledge.recyclerview.layoutmanager.flow.FlowLayoutManagerActivity;
import com.example.knowledge.recyclerview.layoutmanager.path.PathLayoutManagerActivity;
import com.example.knowledge.recyclerview.snaphelper.SnapHelperActivity;
import com.example.knowledge.recyclerview.stagger.RVStaggerActivity;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
    }

    public void toRVCrudActivity(View view) {
        startActivity(new Intent(this, RVCrudActivity.class));
    }

    public void toRVStaggerActivity(View view) {
        startActivity(new Intent(this, RVStaggerActivity.class));
    }

    public void toRVItemDecorationActivity(View view) {
        startActivity(new Intent(this, RVItemDecorationActivity.class));
    }

    public void toSnapHelperActivity(View view) {
        startActivity(new Intent(this, SnapHelperActivity.class));
    }

    public void toLayoutManagerActivity(View view) {
        startActivity(new Intent(this, LayoutManagerActivity.class));
    }


}
