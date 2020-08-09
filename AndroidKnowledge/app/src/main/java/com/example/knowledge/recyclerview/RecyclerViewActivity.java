package com.example.knowledge.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.itemdecoration.RVItemDecorationActivity;
import com.example.knowledge.recyclerview.layoutmanager.card.CardActivity;
import com.example.knowledge.recyclerview.layoutmanager.flow.FlowActivity;
import com.example.knowledge.recyclerview.layoutmanager.path.PathActivity;
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

    public void toFlowLayoutActivity(View view) {
        startActivity(new Intent(this, FlowActivity.class));
    }

    public void toSnapHelperActivity(View view) {
        startActivity(new Intent(this, SnapHelperActivity.class));
    }

    public void toCardActivity(View view) {
        startActivity(new Intent(this, CardActivity.class));
    }

    public void toPathActivity(View view) {
        startActivity(new Intent(this, PathActivity.class));
    }

}
