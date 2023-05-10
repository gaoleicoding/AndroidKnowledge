package com.example.knowledge.component.recyclerview.snaphelper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge.R;

import java.util.ArrayList;

public class SnapHelperActivity extends AppCompatActivity {
    private RecyclerView mRecycleview;
    private ArrayList<TestBean> mDatas;
    private int[] imgs = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap);
        mRecycleview = (RecyclerView) findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleview.setLayoutManager(linearLayoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecycleview);
        mDatas = new ArrayList<>();
        for (int x = 0; x < imgs.length; x++) {
            TestBean testBean = new TestBean();
            testBean.setText("世界中从不缺少美，而是缺少发现美的眼睛。在艺术者眼中，一切都是美的，因为他锐利的慧眼，注视到一切众生万物之核心；如能抉发其品性，就是透入外形触及其内在的\"真\"。此\"真\"，也即是\"美\"。");
            testBean.setImgs(imgs[x]);
            mDatas.add(testBean);
        }
        SnapAdapter snapAdapter = new SnapAdapter(this, mDatas);
        mRecycleview.setAdapter(snapAdapter);
    }
}
