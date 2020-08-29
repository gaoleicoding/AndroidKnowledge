package com.example.knowledge.recyclerview.layoutmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.RVCrudActivity;
import com.example.knowledge.recyclerview.itemdecoration.RVItemDecorationActivity;
import com.example.knowledge.recyclerview.layoutmanager.card.CardLayoutManagerActivity;
import com.example.knowledge.recyclerview.layoutmanager.flow.FlowLayoutManagerActivity;
import com.example.knowledge.recyclerview.layoutmanager.path.PathLayoutManagerActivity;
import com.example.knowledge.recyclerview.snaphelper.SnapHelperActivity;
import com.example.knowledge.recyclerview.stagger.RVStaggerActivity;

public class LayoutManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutmanager);
    }

    public void toCardLayoutManagerActivity(View view) {
        startActivity(new Intent(this, CardLayoutManagerActivity.class));
    }

    public void toFlowLayoutManagerActivity(View view) {
        startActivity(new Intent(this, FlowLayoutManagerActivity.class));
    }

    public void toPathLayoutManagerActivity(View view) {
        startActivity(new Intent(this, PathLayoutManagerActivity.class));
    }

}
