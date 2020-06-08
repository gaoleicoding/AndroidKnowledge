package com.example.knowledge.recyclerview;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    private RecyclerView recyclerView;
    private String[] bookArray, bookDesArray;
    private int[] resIds;

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
        //为RecyclerView添加HeaderView和FooterView
        setHeaderView(recyclerView);
        setFooterView(recyclerView);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mDatas);
        mAdapter.setOnItemClickLitener(new DiffAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < mDatas.size()) {
                    Toast.makeText(RecyclerActivity.this, "position:" + position + " , name:"
                            + mDatas.get(position - mAdapter.getHeaderCount()).getName(), Toast.LENGTH_SHORT).show();
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

    private void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.item_header, view, false);
        mAdapter.addHeaderView(header);
    }

    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(this).inflate(R.layout.item_footer, view, false);
        mAdapter.addFooterView(footer);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        bookArray = getResources().getStringArray(R.array.array_book);
        bookDesArray = getResources().getStringArray(R.array.array_book_des);
        TypedArray ar = getResources().obtainTypedArray(R.array.array_book_icon);
        int len = ar.length();
        resIds = new int[len];
        for (int i = 0; i < len; i++) {
            resIds[i] = ar.getResourceId(i, 0);//资源的id
        }
        for (int i = 0; i < 5; i++) {
            mDatas.add(new Book(bookArray[i], bookDesArray[i], resIds[i]));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
//                addData(mDatas.size() - 1);
                addDataByDiffUtil(mDatas.size() - 1);
                break;
            case R.id.delete:
//                removeData(mDatas.size() - 2);
                removeDataByDiffUtil(mDatas.size() - 2);
                break;
            case R.id.update:
//                updateData(1);
//                updateDataByPayload(1);
                updateDataByDiffUtil(1);
//                updateDataByDiffUtilAndPayload(1);
                break;
        }
    }

    public void addData(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        Book testBean = new Book(bookArray[6] + ++count, bookDesArray[6] + ++count, resIds[6]);
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
        //将改动的position刷新一遍，从而再次取值时，不会再出现错乱现象。
        mAdapter.notifyItemRangeChanged(position, mDatas.size() - 1);
    }

    int count = 1;

    public void updateData(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        Book book = mDatas.get(position);
        book.setDesc("android_updateData" + ++count);
        //更新整个item
        mAdapter.notifyItemChanged(position);

    }

    public void updateDataByPayload(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        //RecyclerView 可以去掉item刷新动画
        //((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        Book book = mDatas.get(position);
        //模拟更新book的desc
        book.setDesc("android_updateDataByPayload" + ++count);
        //把更新放到Bundle中，可以不止一处
        Bundle payloadBundle = new Bundle();
        payloadBundle.putString("KEY_DESC", "android_updateDataByPayload");
        mAdapter.notifyItemChanged(position, payloadBundle);

    }

    public void addDataByDiffUtil(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据
            }
            Book testBean = new Book(bookArray[7] + ++count, bookDesArray[7] + ++count, resIds[7]);
            mNewDatas.add(position, testBean);
            mAdapter.setNewDiffData(mNewDatas);

            mDatas = mNewDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeDataByDiffUtil(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                //深clone一遍旧数据
                mNewDatas.add(bean.clone());
            }
            mNewDatas.remove(position);
            mAdapter.setNewDiffData(mNewDatas);
            mDatas = mNewDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDataByDiffUtil(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                //clone一遍旧数据 ，模拟刷新操作
                mNewDatas.add(bean.clone());
            }
            Book testBean = mNewDatas.get(position);
            //模拟修改数据
            testBean.setDesc("android_updateDataByDiffUtil" + ++count);
            mAdapter.setNewDiffData(mNewDatas);
            mDatas = mNewDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDataByDiffUtilAndPayload(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                //clone一遍旧数据 ，模拟刷新操作
                mNewDatas.add(bean.clone());
            }
            Book testBean = mNewDatas.get(position);
            //模拟修改数据
            testBean.setDesc("android_updateByDiffUtilAndPayload" + ++count);
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
