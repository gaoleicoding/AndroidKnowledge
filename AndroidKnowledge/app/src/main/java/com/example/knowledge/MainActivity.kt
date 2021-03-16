package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.protobuf.ProtoBufActivity
import com.example.knowledge.adapter.ItemAdapter
import com.example.knowledge.annotation.AnnotationActivity
import com.example.knowledge.asynctask.AsyncActivity
import com.example.knowledge.contentprovider.ProviderActivity
import com.example.knowledge.cryptography.CryptoActivity
import com.example.knowledge.lambda.LambdaActivity
import com.example.knowledge.ninepatch.NinePatchActivity
import com.example.knowledge.popupwindow.PopupActivity
import com.example.knowledge.recyclerview.RecyclerViewActivity

class MainActivity : AppCompatActivity() {
    var items = arrayOf(
            "SecondActivity",
            "EncryptActivity",
            "LambdaActivity",
            "ProviderActivity",
            "NinePatchActivity",
            "RecyclerViewActivity",
            "AsyncActivity",
            "PopupActivity",
            "AnnotationActivity",
            "ProtoBufActivity"
    )
    var activities = arrayOf<Class<*>>(SecondActivity::class.java, CryptoActivity::class.java,  LambdaActivity::class.java,
            ProviderActivity::class.java, NinePatchActivity::class.java, RecyclerViewActivity::class.java, AsyncActivity::class.java,
            PopupActivity::class.java, AnnotationActivity::class.java, ProtoBufActivity::class.java)
    private val a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("info", "MainActivity--onCreate()")
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


    override fun onStart() {
        super.onStart()
        Log.i("info", "MainActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("info", "MainActivity--onResumed()")
    }

    override fun onPause() {
        super.onPause()
        Log.i("info", "MainActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("info", "MainActivity--onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("info", "MainActivity--onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("info", "MainActivity--onDestroy()")
    }
}