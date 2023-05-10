package com.example.knowledge.component.recyclerview.horizontal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.R

class RvAdapter : RecyclerView.Adapter<RvAdapter.MyViewHolder>() {
    private var mDataList = mutableListOf<String>()
    private lateinit var mContext: Context


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = mDataList[position]
        holder.name.text = data
        holder.itemView.setOnClickListener {
            setOnItemClickListener(data)
        }
    }


    fun setData(dataList: List<String>) {
        mDataList.clear()
        mDataList.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mDataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_horizontal_rv, parent, false)
        return MyViewHolder(view)
    }

    private fun setOnItemClickListener(data: String) {
        Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show()
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView

        init {
            name = itemView.findViewById(R.id.name)
        }
    }

}