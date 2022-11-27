package com.example.knowledge.recyclerview;

import android.graphics.Canvas;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.knowledge.R;
import com.example.knowledge.recyclerview.diffutil.DiffAdapter;

import java.util.Collections;
import java.util.List;

public class ItemHelperCallBack extends ItemTouchHelper.Callback {

    private List<Book> mDatas;
    private DiffAdapter mAdapter;

    public void setmDatas(List<Book> mDatas) {
        this.mDatas = mDatas;
    }

    public void setmAdapter(DiffAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    /**
     * 设置滑动类型标记监控上下左右
     * 返回一个整数类型的标识，用于判断Item那种移动行为是允许的
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        //这个地方的设置比较重要，设置什么的dragFlags和swipeFlags
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager
                || recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {

            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖拽切换Item的回调
     * 如果Item切换了位置，返回true；反之，返回false
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        //得到当拖拽的viewHolder的Position
        int fromPosition = viewHolder.getAdapterPosition();
        //拿到当前拖拽到的item的viewHolder
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i = fromPosition-mAdapter.getHeaderCount(); i < toPosition-mAdapter.getHeaderCount(); i++) {
                Collections.swap(mDatas, i, i + 1);
            }
        } else {
            for (int i = fromPosition-mAdapter.getHeaderCount(); i > toPosition-mAdapter.getHeaderCount(); i--) {
                Collections.swap(mDatas, i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        //将改动的position刷新一遍，从而再次取值时，不会再出现错乱现象。
        mAdapter.notifyItemRangeChanged(Math.min(fromPosition, toPosition), mAdapter.getItemCount() - Math.min(fromPosition, toPosition) - mAdapter.getFooterCount());
        return true;
    }


    /**
     * 滑动删除Item的操作
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //这个主要是做左右滑动的回调

        int position = viewHolder.getAdapterPosition();
        if (position > mAdapter.getHeaderCount() - 1) {
            mAdapter.notifyItemRemoved(position);
            mDatas.remove(position - mAdapter.getHeaderCount());
            //将改动的position刷新一遍，从而再次取值时，不会再出现错乱现象。
            mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount() - position - mAdapter.getFooterCount());
        }
    }

    /**
     * 设置Item不支持长按拖动
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * Item被选中时候，改变Item的背景
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundResource(R.drawable.shape_corner_item_drag);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 移动过程中重新绘制Item，随着滑动的距离，设置Item的透明度
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        float x = Math.abs(dX) + 0.5f;
        float width = viewHolder.itemView.getWidth();
        float alpha = 1f - x / width;
        viewHolder.itemView.setAlpha(alpha);
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                isCurrentlyActive);
    }

    /**
     * 用户操作完毕或者动画完毕后调用，恢复item的背景和透明度
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundResource(R.drawable.shape_corner_item);
        viewHolder.itemView.setAlpha(1.0f);
    }

}

