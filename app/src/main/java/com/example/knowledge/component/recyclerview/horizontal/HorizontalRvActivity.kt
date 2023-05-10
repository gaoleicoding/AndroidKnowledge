package com.example.knowledge.component.recyclerview.horizontal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.databinding.ActivityHorizontalRvBinding

class HorizontalRvActivity : AppCompatActivity() {

    private val mBinding: ActivityHorizontalRvBinding by lazy {
        ActivityHorizontalRvBinding.inflate(layoutInflater)
    }
    lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initRecyclerView()
        loadData()
    }

    private fun initRecyclerView() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adapter = RvAdapter()
        mBinding.recyclerView.adapter = adapter
        val snapHelper = LeftLinearSnapHelper()
        snapHelper.attachToRecyclerView(mBinding.recyclerView)

    }

    private fun loadData() {
        val data = ArrayList<String>(100)
        for (i in 0..99) {
            data.add("text-$i")
        }
        adapter.setData(data)
    }
}