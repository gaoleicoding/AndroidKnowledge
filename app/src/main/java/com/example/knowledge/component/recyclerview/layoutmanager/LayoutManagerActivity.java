package com.example.knowledge.component.recyclerview.layoutmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.component.recyclerview.layoutmanager.card.CardLayoutManagerActivity;
import com.example.knowledge.component.recyclerview.layoutmanager.flow.FlowLayoutManagerActivity;
import com.example.knowledge.component.recyclerview.layoutmanager.path.PathLayoutManagerActivity;

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
