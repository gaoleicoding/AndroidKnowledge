package com.example.knowledge.recyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.diffutil.DiffAdapter;
import com.example.knowledge.recyclerview.diffutil.DiffCallBack;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {
    private List<TestBean> mDatas, mNewDatas;
    private DiffAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initData();
        RecyclerView mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiffAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new TestBean("Android", "Android", R.drawable.android));
        mDatas.add(new TestBean("IOS", "IOS", R.drawable.ios));
        mDatas.add(new TestBean("JAVA", "JAVA", R.drawable.java));
        mDatas.add(new TestBean("C#", "C#", R.drawable.c));
        mDatas.add(new TestBean("Python", "Python", R.drawable.python));
    }

    /**
     * 模拟刷新操作
     *
     * @param view
     */
    public void onAdd(View view) {
        try {
            mNewDatas = new ArrayList<>();
            for (TestBean bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据
            }
            TestBean testBean = new TestBean("php", "python", R.drawable.php);
            mNewDatas.add(testBean);
            calculateDiff();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDelete(View view) {
        try {
            mNewDatas = new ArrayList<>();
            for (TestBean bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据
            }
            mNewDatas.remove(0);
            calculateDiff();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpdate(View view) {
        try {
            mNewDatas = new ArrayList<>();
            for (TestBean bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据 ，模拟刷新操作
            }
            mNewDatas.get(0).setDesc("Android12345");
            mNewDatas.get(0).setPic(R.drawable.android);//模拟修改数据

            calculateDiff();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void calculateDiff() {
        //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                //放在子线程中计算DiffResult
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, mNewDatas), true);
                Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                message.obj = diffResult;//obj存放DiffResult
                message.sendToTarget();
            }
        }).start();
        //mAdapter.notifyDataSetChanged();//以前普通青年的我们只能这样，现在我们是文艺青年了，有新宠了
    }

    private static final int H_CODE_UPDATE = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case H_CODE_UPDATE:
                    //取出Result
                    DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult) msg.obj;
                    //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，轻松成为文艺青年
                    diffResult.dispatchUpdatesTo(mAdapter);

                    //这种方法可以fix add 0 不滑动
                    /*diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
                        @Override
                        public void onInserted(int position, int count) {
                            mAdapter.notifyItemRangeInserted(position, count);
                            if (position==0){
                                mRv.scrollToPosition(0);
                            }
                        }

                        @Override
                        public void onRemoved(int position, int count) {
                            mAdapter.notifyItemRangeRemoved(position, count);
                        }

                        @Override
                        public void onMoved(int fromPosition, int toPosition) {
                            mAdapter.notifyItemMoved(fromPosition, toPosition);
                        }

                        @Override
                        public void onChanged(int position, int count, Object payload) {
                            mAdapter.notifyItemRangeChanged(position, count, payload);
                        }
                    });*/

                    //别忘了将新数据给Adapter
                    mAdapter.setDatas(mNewDatas);
                    break;
            }
        }
    };

}
