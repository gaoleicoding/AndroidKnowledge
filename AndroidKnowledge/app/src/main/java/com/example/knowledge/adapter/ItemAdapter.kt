package com.example.knowledge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.R


class ItemAdapter(private val ctx: Context, private val list: List<String>) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>(), View.OnClickListener {

    override fun onClick(v: View) {
        mOnItemClickLitener?.onItemClick(v)
    }

    fun setOnItemClickLitener(mOnItemClickLitener: OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener
    }

    private var mOnItemClickLitener: OnItemClickLitener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_items, null)
        val holder = MyViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvContent.text = list.get(position)
        if (mOnItemClickLitener != null) {
            holder.rootView.setOnClickListener(this);
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvContent: TextView
        val rootView: LinearLayout

        init {
            tvContent = itemView.findViewById(R.id.tv_content) as TextView
            rootView = itemView.findViewById(R.id.root_view) as LinearLayout
        }

    }

    interface OnItemClickLitener {
        fun onItemClick(v: View)
    }
}
