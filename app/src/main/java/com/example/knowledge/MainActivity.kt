package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import android.widget.ExpandableListView
import android.widget.ExpandableListView.OnChildClickListener
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.adapter.MyExListAdapter
import com.example.knowledge.bean.ChildBean

class MainActivity : AppCompatActivity() {
    private lateinit var expandableListView: ExpandableListView
    private var groups = mutableListOf<String>()
    private var children = mutableListOf<List<ChildBean>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        expandableListView = findViewById(R.id.eplv_point)
        val myExListAdapter = MyExListAdapter(this@MainActivity, groups, children)
        expandableListView.setAdapter(myExListAdapter)
        expandableListView.setOnChildClickListener(OnChildClickListener { parent, v, groupPosition, childPosition, id ->
            try {
                val c1 = Class.forName(children[groupPosition][childPosition].className)
                startActivity(Intent(this@MainActivity, c1))
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e)
            }
            false
        })
    }

    //设置一下模拟的数据
    private fun initData() {
        val items: MutableList<ChildBean> = ArrayList() //组别中的数据
        groups.add("四大组件之Activity")
        groups.add("四大组建之ContentProvider")
        groups.add("四大组件之Service")
        groups.add("四大组件之Broadcast")
        groups.add("多媒体之图片")
        groups.add("多媒体之音视频")
        groups.add("安卓组件")
        groups.add("Jetpack")
        groups.add("安全")
        groups.add("安卓知识点")
        items.add(ChildBean("SecondActivity", "com.example.knowledge.activity.SecondActivity"))
        items.add(
            ChildBean(
                "TestAndroidActivity",
                "com.example.knowledge.activity.TestAndroidActivity"
            )
        )
        items.add(
            ChildBean(
                "TestKotlinActivity",
                "com.example.knowledge.activity.TestKotlinActivity"
            )
        )
        children.add(ArrayList(items))
        items.clear()
        items.add(ChildBean("ProviderActivity", "com.example.knowledge.provider.ProviderActivity"))
        children.add(ArrayList(items))
        items.clear()
        items.add(ChildBean("ServiceActivity", "com.example.knowledge.service.ServiceActivity"))
        children.add(ArrayList(items))
        items.clear()
        items.add(
            ChildBean(
                "BroadcastActivity",
                "com.example.knowledge.broadcast.BroadcastActivity"
            )
        )
        children.add(ArrayList(items))
        items.clear()
        items.add(
            ChildBean(
                "NinePatchActivity",
                "com.example.knowledge.image.ninepatch.NinePatchActivity"
            )
        )
        items.add(ChildBean("ImgProcessActivity", "com.example.knowledge.image.ImgProcessActivity"))
        items.add(
            ChildBean(
                "PhotoSelectActivity",
                "com.example.knowledge.image.photo.PhotoSelectActivity"
            )
        )
        children.add(ArrayList(items))
        items.clear()

        items.add(
            ChildBean(
                "AudioActivity",
                "com.example.knowledge.audio.AudioActivity"
            )
        )
        items.add(
            ChildBean(
                "VideoPlayActivity",
                "com.example.knowledge.video.VideoPlayActivity"
            )
        )
        children.add(ArrayList(items))
        items.clear()
        items.add(
            ChildBean(
                "WebViewActivity",
                "com.example.knowledge.component.webview.WebViewActivity"
            )
        )
        items.add(
            ChildBean(
                "RecyclerViewActivity",
                "com.example.knowledge.component.recyclerview.RecyclerViewActivity"
            )
        )
        items.add(
            ChildBean(
                "PopupActivity",
                "com.example.knowledge.component.popupwindow.PopupActivity"
            )
        )
        children.add(ArrayList(items))
        items.clear()
        items.add(
            ChildBean(
                "DataStoreActivity",
                "com.example.knowledge.jetpack.datastore.DataStoreActivity"
            )
        )
        children.add(ArrayList(items))
        items.clear()
        items.add(ChildBean("CryptoActivity", "com.example.knowledge.security.CryptoActivity"))
        children.add(ArrayList(items))
        items.clear()
        items.add(
            ChildBean(
                "StorageActivity",
                "com.example.knowledge.android.storage.StorageActivity"
            )
        )
        items.add(
            ChildBean(
                "LocationActivity",
                "com.example.knowledge.android.LBS.LocationActivity"
            )
        )
        items.add(ChildBean("FontActivity", "com.example.knowledge.android.font.FontActivity"))
        items.add(
            ChildBean(
                "AnnotationActivity",
                "com.example.knowledge.android.annotation.AnnotationActivity"
            )
        )
        children.add(ArrayList(items))
        items.clear()
    }
}