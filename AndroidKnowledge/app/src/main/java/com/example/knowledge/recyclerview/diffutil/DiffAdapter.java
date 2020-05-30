package com.example.knowledge.recyclerview.diffutil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.Book;

import java.util.List;

public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.DiffViewHolder> {
    private final static String TAG = "DiffAdapter";
    private List<Book> mDatas;
    private LayoutInflater mInflater;
    public DiffViewHolder viewHolder;

    public DiffAdapter(Context mContext) {
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<Book> mDatas) {
        this.mDatas = mDatas;
    }

    public List<Book> getmDatas() {
        return mDatas;
    }

    @Override
    public DiffViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolder = new DiffViewHolder(mInflater.inflate(R.layout.item_book, parent, false));
    }

    @Override
    public void onBindViewHolder(final DiffViewHolder holder, final int position) {
        Book bean = mDatas.get(position);
        holder.tvTitle.setText(bean.getName());
        holder.tvDesc.setText(bean.getDesc());
        holder.ivBook.setImageResource(bean.getPic());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                bean.setSelected(!cb.isChecked());
            }
        });

        //通过为条目设置点击事件触发回调
        if (mOnItemClickLitener != null) {
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(DiffViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            //文艺青年中的文青
            //取出我们在DiffCallBack的getChangePayload() 或者 mAdapter.notifyItemChanged(position, payloadBundle)中的payloadBundle;
            Bundle payload = (Bundle) payloads.get(0);
            Book bean = mDatas.get(position);
            for (String key : payload.keySet()) {
                switch (key) {
                    case "KEY_NAME":
                        holder.tvTitle.setText(bean.getName());
                        break;
                    case "KEY_DESC":
                        holder.tvDesc.setText(bean.getDesc());
                        break;
                    case "KEY_PIC":
                        holder.ivBook.setImageResource(payload.getInt(key));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void setNewData(@Nullable List<Book> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    private List<Book> mNewDatas;

    public void setNewDiffData(@Nullable List<Book> mNewDatas) {
        if (mNewDatas == null) return;
        this.mNewDatas = mNewDatas;
//        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, mNewDatas), true);
//        diffResult.dispatchUpdatesTo(this);
//        mDatas = mNewDatas;

        new Thread(new Runnable() {
            @Override
            public void run() {
                //放在子线程中计算DiffResult
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, mNewDatas), true);
                Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                //obj存放DiffResult
                message.obj = diffResult;
                message.sendToTarget();
            }
        }).start();

    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    static class DiffViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivBook;
        CheckBox checkBox;
        RelativeLayout itemLayout;

        DiffViewHolder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.item_layout);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            ivBook = itemView.findViewById(R.id.iv_book);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
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
                    diffResult.dispatchUpdatesTo(DiffAdapter.this);

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
                    mDatas = mNewDatas;
                    //别忘了将新数据给Adapter
                    break;
            }
        }
    };
    private OnItemClickLitener mOnItemClickLitener;

    //设置回调接口
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
