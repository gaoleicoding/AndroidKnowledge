package com.example.knowledge.component.recyclerview.diffutil;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListUpdateCallback;

public final class BaseQuickAdapterListUpdateCallback implements ListUpdateCallback {

    @NonNull
    private final DiffAdapter mAdapter;

    public BaseQuickAdapterListUpdateCallback(@NonNull DiffAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void onInserted(int position, int count) {
        this.mAdapter.notifyItemRangeInserted(position + mAdapter.getHeaderCount(), count);
    }

    public void onRemoved(int position, int count) {
        this.mAdapter.notifyItemRangeRemoved(position + mAdapter.getHeaderCount(), count);
    }

    public void onMoved(int fromPosition, int toPosition) {
        this.mAdapter.notifyItemMoved(fromPosition + mAdapter.getHeaderCount(), toPosition + mAdapter.getHeaderCount());
    }

    @Override
    public void onChanged(int position, int count, @Nullable Object payload) {
        this.mAdapter.notifyItemRangeChanged(position + mAdapter.getHeaderCount(), count, payload);
    }
}
