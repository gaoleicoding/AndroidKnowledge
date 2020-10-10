package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.adapter.ItemAdapter
import com.example.knowledge.asynctask.AsyncActivity
import com.example.knowledge.contentprovider.ProviderActivity
import com.example.knowledge.decrypt.DecryptActivity
import com.example.knowledge.design.CollapseActivity
import com.example.knowledge.lambda.LambdaActivity
import com.example.knowledge.ninepatch.NinePatchActivity
import com.example.knowledge.path.PathActivity
import com.example.knowledge.popupwindow.PopupActivity
import com.example.knowledge.recyclerview.RecyclerViewActivity

class MainActivity : AppCompatActivity() {
    var items = arrayOf(
            "SecondActivity",
            "EncryptActivity",
            "CollapseActivity",
            "LambdaActivity",
            "ProviderActivity",
            "NinePatchActivity",
            "RecyclerViewActivity",
            "AsyncActivity",
            "PathActivity",
            "PopupActivity")
    var activities = arrayOf<Class<*>>(SecondActivity::class.java, DecryptActivity::class.java, CollapseActivity::class.java, LambdaActivity::class.java,
            ProviderActivity::class.java, NinePatchActivity::class.java, RecyclerViewActivity::class.java, AsyncActivity::class.java, PathActivity::class.java,
            PopupActivity::class.java)
    private val a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        val layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        val itemAdapter = ItemAdapter(this, items)
        recyclerview.adapter = itemAdapter
        recyclerview.addItemDecoration(
                DividerItemDecoration(this@MainActivity,
                        DividerItemDecoration.VERTICAL
                )
        )
        itemAdapter.setOnItemClickLitener(object : ItemAdapter.OnItemClickLitener {
            override fun onItemClick(v: View) {
                val position = recyclerview.getChildAdapterPosition(v)
                startActivity(Intent(this@MainActivity, activities[position]))
            }
        })
    }
}