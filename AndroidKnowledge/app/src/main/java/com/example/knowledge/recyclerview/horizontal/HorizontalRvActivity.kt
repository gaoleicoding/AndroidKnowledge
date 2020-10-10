package com.example.knowledge.recyclerview.horizontal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.R
import kotlinx.android.synthetic.main.activity_horizontal_rv.*

class HorizontalRvActivity : AppCompatActivity() {
    lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_rv)
        initRecyclerView()
        loadData()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapter = RvAdapter()
        recyclerView.adapter = adapter
        val snapHelper = LeftLinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

    }

    private fun loadData() {
        val data = ArrayList<String>(100)
        for (i in 0..99) {
            data.add("text-$i")
        }
        adapter.setData(data)
    }
}