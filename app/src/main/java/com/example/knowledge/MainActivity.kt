package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowledge.LBS.LocationActivity
import com.example.knowledge.adapter.ItemAdapter
import com.example.knowledge.annotation.AnnotationActivity
import com.example.knowledge.asynctask.AsyncActivity
import com.example.knowledge.contentprovider.ProviderActivity
import com.example.knowledge.cryptography.CryptoActivity
import com.example.knowledge.datastore.DataStoreActivity
import com.example.knowledge.lambda.LambdaActivity
import com.example.knowledge.ninepatch.NinePatchActivity
import com.example.knowledge.popupwindow.PopupActivity
import com.example.knowledge.process.ProcessActivity
import com.example.knowledge.recyclerview.RecyclerViewActivity
import com.example.knowledge.service.ServiceActivity
import com.example.knowledge.utils.LogUtil
import com.example.knowledge.view.ViewActivity
import com.tencent.mmkv.MMKV


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    var items = arrayOf(
        "SecondActivity",
        "加解密",
        "Lambda",
        "四大组建之ContentProvider",
        "四大组件-Service",
        "NinePatch",
        "RecyclerView",
        "AsyncTask",
        "弹窗",
        "注解",
        "Jetpack",
        "进程保活",
        "位置服务",
        "安卓组件",
    )
    var activities = arrayOf<Class<*>>(
        SecondActivity::class.java,
        CryptoActivity::class.java,
        LambdaActivity::class.java,
        ProviderActivity::class.java,
        ServiceActivity::class.java,
        NinePatchActivity::class.java,
        RecyclerViewActivity::class.java,
        AsyncActivity::class.java,
        PopupActivity::class.java,
        AnnotationActivity::class.java,
        DataStoreActivity::class.java,
        ProcessActivity::class.java,
        LocationActivity::class.java,
        ViewActivity::class.java,
    )
    private val a = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(TAG, "MainActivity--onCreate()")
        setContentView(R.layout.activity_main)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        val layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        val itemAdapter = ItemAdapter(this, items)
        recyclerview.adapter = itemAdapter
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
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
        LogUtil.d(TAG, "testMMKV--bool: " + mmkv.decodeBool("bool"))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        LogUtil.d(TAG, "onWindowFocusChanged: " + hasFocus)
        if (hasFocus) {

        } else {

        }
        super.onWindowFocusChanged(hasFocus)
    }
    override fun onStart() {
        super.onStart()
        LogUtil.d(TAG, "MainActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(TAG, "MainActivity--onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d(TAG, "MainActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d(TAG, "MainActivity--onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.d(TAG, "MainActivity--onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "MainActivity--onDestroy()")
    }

}