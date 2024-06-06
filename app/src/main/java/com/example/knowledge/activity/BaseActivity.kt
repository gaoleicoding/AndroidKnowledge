package com.example.knowledge.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.R

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mTitleTv: TextView
    lateinit var rl_title: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parentView: ViewGroup =
            View.inflate(this@BaseActivity, R.layout.activity_base, null) as ViewGroup
        val childView = View.inflate(this@BaseActivity, getLayoutId(), null)
        parentView.addView(childView)
        setContentView(parentView)

        mTitleTv = parentView.findViewById(R.id.tv_title)
        rl_title = parentView.findViewById(R.id.rl_title)
        val mBackIv = findViewById<ImageView>(R.id.iv_back)
        mBackIv.setOnClickListener {
            finish()
        }

        initView()
        initData()
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData()

}