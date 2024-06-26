package com.example.knowledge.component.recyclerview.stagger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.knowledge.R;
import com.fifedu.lib_common_utils.SystemUtil;

import java.util.List;

public class StaggerAdapter extends RecyclerView.Adapter<StaggerAdapter.LinearHolder> {
    private final List<WaterfullBean> list;
    private final Context context;
    int itemWidth;

    public StaggerAdapter(List<WaterfullBean> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LinearHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_stagger_item, parent, false);
        //new一个我们的ViewHolder，findViewById操作都在LinearHolder的构造方法中进行了
        LinearHolder simpleViewHolder = new LinearHolder(view);
        simpleViewHolder.setIsRecyclable(true);
        return simpleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearHolder holder, int position) {
        holder.recycler_item.setText(list.get(position).tv);
        //动态设置控件宽高
        //先算出item的宽度，给RecyclerView设置完间隔后，屏幕宽度-间隔*3 就是两个item的宽度和了，
        // 故 itemWidth=（ScreenWidth-间隔*3）/2 ，换算过程，记得只有最终结果转int，计算过程用float，防止莫名其妙的四舍五入导致height过多偏差。
        ViewGroup.LayoutParams layoutParams = holder.imgRV.getLayoutParams();
        int itemWidth = (SystemUtil.getScreenWidth(context) - 5 * 3) / 2;
        layoutParams.width = itemWidth;
        float scale = (itemWidth + 0f) / 350;//这儿是图片的宽度 目前写死了 自己可以根据自己实际情况替换掉
        layoutParams.height = (int) (Integer.parseInt(list.get(position).imgHeight) * scale);
        holder.imgRV.setLayoutParams(layoutParams);
        Glide.with(context).load(list.get(position).img).override(layoutParams.width, layoutParams.height).into(holder.imgRV);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LinearHolder extends RecyclerView.ViewHolder {
        TextView recycler_item;
        ImageView imgRV;

        public LinearHolder(View itemView) {
            super(itemView);
            recycler_item = itemView.findViewById(R.id.recycler_item_tv);
            imgRV = itemView.findViewById(R.id.imgRV);
        }
    }
}