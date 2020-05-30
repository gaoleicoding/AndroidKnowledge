package com.example.knowledge.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.diffutil.DiffAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Book> mDatas;
    private DiffAdapter mAdapter;
    private Button btnAdd, btnDelete, btnUpdate;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        btnAdd = findViewById(R.id.add);
        btnDelete = findViewById(R.id.delete);
        btnUpdate = findViewById(R.id.update);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        initData();
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiffAdapter(this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mDatas);
        mAdapter.setOnItemClickLitener(new DiffAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < mDatas.size()) {
                    Toast.makeText(RecyclerActivity.this, "position:" + position + " , name:" + mDatas.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        ItemHelperCallBack callback = new ItemHelperCallBack();
        callback.setmDatas(mDatas);
        callback.setmAdapter(mAdapter);
        //创建item helper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        //绑定到recyclerView上面
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new Book("Android", "android", R.drawable.android));
        mDatas.add(new Book("IOS", "ios", R.drawable.ios));
        mDatas.add(new Book("JAVA", "java", R.drawable.java));
        mDatas.add(new Book("C", "c", R.drawable.c));
        mDatas.add(new Book("Python", "python", R.drawable.python));

//        mDatas.add(new TestBean("H5", "h5", R.drawable.h5));
//        mDatas.add(new TestBean("Game", "game", R.drawable.game));
//        mDatas.add(new TestBean("AI", "ai", R.drawable.ai));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addData(mDatas.size() - 1);
//                addByDiffUtil(mDatas.size());
                break;
            case R.id.delete:
                removeData(mDatas.size() - 2);
//                deleteByDiffUtil();
                break;
            case R.id.update:
                updateData(0);
                //updateDataByPayload(0);
//                updateByDiffUtil(0);
//                updateByDiffUtilAndPayload(0);
                break;
        }
    }

    public void addData(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        Book testBean = new Book("Unity" + ++count, "unity" + ++count, R.drawable.unity);
        mDatas.add(position, testBean);
        //通知演示插入动画
        mAdapter.notifyItemInserted(position);
        //通知某一范围内的数据与界面重新绑定
        mAdapter.notifyItemRangeChanged(position, mDatas.size() - position);
        //通知重新绑定所有数据与界面
        //mAdapter.notifyDataSetChanged();
        //通知重新绑定某一个Item的数据与界面
        //mAdapter.notifyItemChanged(position);

        //解决 如果数据确实添加了，就是没有将添加在position 位置的数据显示出来的问题
        //recyclerView.scrollToPosition(position);


    }

    public void removeData(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        mDatas.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    int count = 1;

    public void updateData(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        mDatas.get(position).setName("Game" + ++count);
        mDatas.get(position).setDesc("game" + ++count);
        mDatas.get(position).setPic(R.drawable.game);
        //更新整个item
        mAdapter.notifyItemChanged(position);

    }

    public void updateDataByPayload(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        //RecyclerView 可以去掉item刷新动画
        //((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        Book book = mDatas.get(position);
        //模拟更新book的desc
        book.setDesc("android_payload");
        //把更新放到Bundle中，可以不止一处
        Bundle payloadBundle = new Bundle();
        payloadBundle.putString("KEY_DESC", "android_updateDataByPayload");
        mAdapter.notifyItemChanged(position, payloadBundle);

    }

    public void addByDiffUtil(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据
            }
            Book testBean = new Book("PHP" + ++count, "php" + ++count, R.drawable.php);
            mNewDatas.add(position, testBean);
            mAdapter.setNewDiffData(mNewDatas);
            mDatas = mNewDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteByDiffUtil(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据
            }
            mNewDatas.remove(position);
            mAdapter.setNewDiffData(mNewDatas);
            mDatas = mNewDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateByDiffUtil(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                //clone一遍旧数据 ，模拟刷新操作
                mNewDatas.add(bean.clone());
            }
            Book testBean = mNewDatas.get(position);
            testBean.setName("H5" + ++count);
            testBean.setDesc("h5" + ++count);
            testBean.setPic(R.drawable.h5);//模拟修改数据
            mAdapter.setNewDiffData(mNewDatas);
            mDatas = mNewDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateByDiffUtilAndPayload(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                //clone一遍旧数据 ，模拟刷新操作
                mNewDatas.add(bean.clone());
            }
            Book testBean = mNewDatas.get(position);
            testBean.setDesc("android_updateByDiffUtilAndPayload");//模拟修改数据
//            testBean.setPic(R.drawable.wechat);
            mAdapter.setNewDiffData(mNewDatas);
            mDatas = mNewDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭RecyclerView默认添加删除刷新动画
     */
    public void closeDefaultAnimator(RecyclerView mRvCustomer) {
        if (null == mRvCustomer) return;
        mRvCustomer.getItemAnimator().setAddDuration(0);
        mRvCustomer.getItemAnimator().setChangeDuration(0);
        mRvCustomer.getItemAnimator().setMoveDuration(0);
        mRvCustomer.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);
    }


}
