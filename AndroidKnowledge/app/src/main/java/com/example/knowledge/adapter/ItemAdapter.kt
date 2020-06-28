package com.example.knowledge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.R


class ItemAdapter(private val ctx: Context, private val items: Array<String>) :
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
        holder.tvContent.text = items[position]
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(this);
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvContent: TextView

        init {
            tvContent = itemView.findViewById(R.id.tv_content) as TextView
        }

    }

    interface OnItemClickLitener {
        fun onItemClick(v: View)
    }
}
