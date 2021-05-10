package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.adapter.ItemAdapter
import com.example.knowledge.annotation.AnnotationActivity
import com.example.knowledge.asynctask.AsyncActivity
import com.example.knowledge.contentprovider.ProviderActivity
import com.example.knowledge.cryptography.CryptoActivity
import com.example.knowledge.datastore.DataStoreActivity
import com.example.knowledge.lambda.LambdaActivity
import com.example.knowledge.ninepatch.NinePatchActivity
import com.example.knowledge.popupwindow.PopupActivity
import com.example.knowledge.process.ProcessAliveActivity
import com.example.knowledge.recyclerview.RecyclerViewActivity
import com.example.knowledge.service.ServiceActivity
import com.tencent.mmkv.MMKV


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
            "DataStoreActivity",
            "ServiceActivity",
            "ProcessAliveActivity"
    )
    var activities = arrayOf<Class<*>>(SecondActivity::class.java, CryptoActivity::class.java, LambdaActivity::class.java,
            ProviderActivity::class.java, NinePatchActivity::class.java, RecyclerViewActivity::class.java, AsyncActivity::class.java,
            PopupActivity::class.java, AnnotationActivity::class.java, DataStoreActivity::class.java, ServiceActivity::class.java,
            ProcessAliveActivity::class.java)
    private val a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("info", "MainActivity--onCreate()")
        setContentView(R.layout.activity_main)
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


//        testMMKV()
    }



    fun testMMKV() {
        val mmkv = MMKV.mmkvWithID("TEST")
        mmkv.encode("bool", true)
        Log.i(TAG, "testMMKV--bool: " + mmkv.decodeBool("bool"))
    }

    override fun onStart() {
        super.onStart()
        Log.i("info", "MainActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i("info", "MainActivity--onResume()")
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

    companion object {
        private const val TAG = "MainActivity"

    }
}