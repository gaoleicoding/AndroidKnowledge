package com.example.knowledge.recyclerview;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.animator.FadeItemAnimator;
import com.example.knowledge.recyclerview.animator.RotateItemAnimator;
import com.example.knowledge.recyclerview.animator.ScaleItemAnimator;
import com.example.knowledge.recyclerview.animator.SlideItemAnimator;
import com.example.knowledge.recyclerview.diffutil.DiffAdapter;

import java.util.ArrayList;
import java.util.List;

public class RVCrudActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Book> mDatas;
    private DiffAdapter mAdapter;
    private Button btnAdd, btnDelete, btnUpdate;
    private RecyclerView recyclerView;
    private String[] bookArray, bookDesArray;
    private int[] resIds;
    private Spinner mSpinner;
    ItemHelperCallBack callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_crud);
        btnAdd = findViewById(R.id.add);
        btnDelete = findViewById(R.id.delete);
        btnUpdate = findViewById(R.id.update);
        mSpinner = findViewById(R.id.spinner);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        initData();
        recyclerView = findViewById(R.id.rv_crud);
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
                if (position - mAdapter.getHeaderCount() >= 0) {
                    Toast.makeText(RVCrudActivity.this, "position:" + position + " , name:"
                            + mDatas.get(position - mAdapter.getHeaderCount()).getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        callback = new ItemHelperCallBack();
        callback.setmDatas(mDatas);
        callback.setmAdapter(mAdapter);
        //创建item helper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        //绑定到recyclerView上面
        itemTouchHelper.attachToRecyclerView(recyclerView);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        recyclerView.setItemAnimator(new FadeItemAnimator());
                        break;
                    case 1:
                        recyclerView.setItemAnimator(new SlideItemAnimator());
                        break;
                    case 2:
                        recyclerView.setItemAnimator(new RotateItemAnimator());
                        break;
                    case 3:
                        recyclerView.setItemAnimator(new ScaleItemAnimator());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        bookDesArray = getResources().getStringArray(R.array.array_book_des_short);
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
//                addData(mDatas.size()-1 );
                addDataByDiffUtil(mDatas.size());
                break;
            case R.id.delete:
//                removeData(mDatas.size() - 1);
                removeDataByDiffUtil(mDatas.size() - 1);
                break;
            case R.id.update:
//                updateData(0);
//                updateDataByPayload(1);
//                updateDataByDiffUtil(1);
                updateDataByDiffUtilAndPayload(0);
                break;
        }
    }

    public void addData(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        Book testBean = new Book(bookArray[6] + ++count, bookDesArray[6] + ++count, resIds[6]);
        mDatas.add(position, testBean);
        //通知演示插入动画
        mAdapter.notifyItemInserted(position + mAdapter.getHeaderCount());
        //通知某一范围内的数据与界面重新绑定
        mAdapter.notifyItemRangeChanged(position + mAdapter.getHeaderCount(), mDatas.size() - position);
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
        mAdapter.notifyItemRangeChanged(position + mAdapter.getHeaderCount(), mDatas.size() - position);
    }

    int count = 1;

    public void updateData(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        Book book = mDatas.get(position);
        book.setDesc("android_updateData" + ++count);
        //更新整个item
        mAdapter.notifyItemChanged(position + mAdapter.getHeaderCount());
    }

    public void addDataByDiffUtil(int position) {
        if (position < 0 || position >= mDatas.size()) return;
        try {
            List<Book> mNewDatas = new ArrayList<>();
            for (Book bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据
            }
            Book testBean = new Book(bookArray[5] + ++count, bookDesArray[5] + ++count, resIds[5]);
            mNewDatas.add(position, testBean);
            mAdapter.setNewDiffData(mNewDatas);
            mDatas = mNewDatas;
            //这个一定要重新设置，因为又重新创建一个List<Book>
            callback.setmDatas(mDatas);
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
            //这个一定要重新设置，因为又重新创建一个List<Book>
            callback.setmDatas(mDatas);
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
