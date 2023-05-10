package com.example.knowledge.component.recyclerview.snaphelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledge.R;

import java.util.List;

public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.RecyclerHolder> {
    private Context mContext;
    private List<TestBean> dataList;

    public SnapAdapter(Context context, List<TestBean> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.snap_item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.textView.setText(dataList.get(position).getText());
        holder.img.setImageResource(dataList.get(position).getImgs());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView img;

        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
