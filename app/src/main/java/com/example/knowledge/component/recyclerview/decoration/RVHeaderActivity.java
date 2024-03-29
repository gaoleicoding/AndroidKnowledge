package com.example.knowledge.component.recyclerview.decoration;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge.R;

import java.util.ArrayList;
import java.util.List;

public class RVHeaderActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<String> data;
    TestAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_header);
        mRecyclerView = (RecyclerView) findViewById(R.id.header_recyclerview);

        initDatas();

        mAdapter = new TestAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutmanager);
        SectionDecoration.GroupInfoCallback callback = new SectionDecoration.GroupInfoCallback() {
            @Override
            public GroupInfo getGroupInfo(int position) {

                /**
                 * 分组逻辑，这里为了测试每5个数据为一组。大家可以在实际开发中
                 * 替换为真正的需求逻辑
                */
                int groupId = position / 5;
                int index = position % 5;
                GroupInfo groupInfo = new GroupInfo(groupId,groupId+"");
                groupInfo.setPosition(index);
                return groupInfo;
            }
        };
        mRecyclerView.addItemDecoration(new SectionDecoration(this,callback));
    }

    /**初始化测试数据*/
    private void initDatas() {
        data = new ArrayList<>();
        for (int i = 0; i < 56;i++) {
            data.add(i+" test ");
        }
    }
}
