package com.example.knowledge

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    lateinit var tvHeader: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initCommonView()
        initView()
    }

    fun initCommonView() {
        tvHeader = findViewById(R.id.tv_title)
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()

    fun onBack(view: View) {
        finish()
    }

}