package com.example.knowledge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.R


class MainItemAdapter(private val ctx: Context, private val items: Array<String>) :
        RecyclerView.Adapter<MainItemAdapter.MyViewHolder>() {

    fun setOnItemClickLitener(mOnItemClickLitener: OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener
    }

    private var mOnItemClickLitener: OnItemClickLitener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_main_items, null)
        val holder = MyViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvContent.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvContent: TextView
        var rootView: RelativeLayout

        init {
            tvContent = itemView.findViewById(R.id.tv_content) as TextView
            rootView = itemView.findViewById(R.id.root_view) as RelativeLayout
            rootView.setOnClickListener {
                mOnItemClickLitener?.onItemClick(rootView)
            };
        }
    }

    interface OnItemClickLitener {
        fun onItemClick(v: View)
    }
}
