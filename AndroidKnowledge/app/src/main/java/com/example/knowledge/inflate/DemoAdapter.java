package com.example.knowledge.inflate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.knowledge.R;

import java.util.List;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {

    private Context mContext;
    private List<String> mEntityList;

    public DemoAdapter(Context context, List<String> entityList) {
        this.mContext = context;
        this.mEntityList = entityList;
    }

    @Override
    public DemoAdapter.DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent,false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DemoAdapter.DemoViewHolder holder, int position) {
        String entity = mEntityList.get(position);
        holder.mText.setText(entity);
    }

    @Override
    public int getItemCount() {
        return mEntityList.size();
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder {

        private TextView mText;

        public DemoViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.item_text);
        }
    }
}