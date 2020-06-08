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
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.Book;

import java.util.List;

public class DiffAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final static String TAG = "DiffAdapter";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_FOOTER = 2;
    private List<Book> mDatas;
    private LayoutInflater mInflater;
    private View mHeaderView;
    private View mFooterView;
    private int headerCount = 0;
    private int footerCount = 0;

    public DiffAdapter(Context mContext) {
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<Book> mDatas) {
        this.mDatas = mDatas;
    }

    public List<Book> getmDatas() {
        return mDatas;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addHeaderView(View headerView) {
        if (headerView != null) {
            mHeaderView = headerView;
            headerCount++;
        }
    }

    public void addFooterView(View footerView) {
        if (footerView != null) {
            mFooterView = footerView;
            footerCount++;
        }
    }

    public int getHeaderCount() {
        return headerCount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEADER:
                viewHolder = new HeaderViewHolder(mHeaderView);
                break;
            case TYPE_FOOTER:
                viewHolder = new FooterViewHolder(mFooterView);
                break;
            case TYPE_CONTENT:
                viewHolder = new ContentViewHolder(mInflater.inflate(R.layout.item_content, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.ivHeader.setImageResource(R.drawable.header);
                break;
            case TYPE_FOOTER:
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                footerViewHolder.tvFooter.setText(R.string.loading);
                break;
            case TYPE_CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
                Book bean = mDatas.get(mHeaderView != null ? position - 1 : position);
                contentViewHolder.tvTitle.setText(bean.getName());
                contentViewHolder.tvDesc.setText(bean.getDesc());
                contentViewHolder.ivBook.setImageResource(bean.getPic());
                contentViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        bean.setSelected(!cb.isChecked());
                    }
                });

                //通过为条目设置点击事件触发回调
                if (mOnItemClickLitener != null) {
                    contentViewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickLitener.onItemClick(view, position);
                        }
                    });
                }
                break;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                if (payloads.isEmpty()) {
                    onBindViewHolder(holder, position);
                }
                break;
            case TYPE_FOOTER:
                if (payloads.isEmpty()) {
                    onBindViewHolder(holder, position);
                }
                break;
            case TYPE_CONTENT:
                ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
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
                                contentViewHolder.tvTitle.setText(bean.getName());
                                break;
                            case "KEY_DESC":
                                contentViewHolder.tvDesc.setText(bean.getDesc());
                                break;
                            case "KEY_PIC":
                                contentViewHolder.ivBook.setImageResource(payload.getInt(key));
                                break;
                            default:
                                break;
                        }
                    }
                }
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_CONTENT;
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

        //子线程中计算diffResult，避免耗时操作影响主线程
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
        if (mHeaderView != null && mFooterView != null) {
            return mDatas.size() + 2;
        } else if (mHeaderView != null) {
            return mDatas.size() + 1;
        } else if (mFooterView != null) {
            return mDatas.size() + 1;
        }
        return mDatas.size();

    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        ImageView ivBook;
        CheckBox checkBox;
        RelativeLayout itemLayout;

        ContentViewHolder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.item_layout);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            ivBook = itemView.findViewById(R.id.iv_book);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        ImageView ivHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ivHeader = itemView.findViewById(R.id.iv_header);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        TextView tvFooter;

        FooterViewHolder(View itemView) {
            super(itemView);
            tvFooter = itemView.findViewById(R.id.tv_footer);
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
//                    diffResult.dispatchUpdatesTo(new BaseQuickAdapterListUpdateCallback(DiffAdapter.this));

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
