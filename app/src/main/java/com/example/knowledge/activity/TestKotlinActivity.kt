package com.example.knowledge.activity

import android.app.Activity
import android.os.Bundle
import com.example.knowledge.R

internal class TestKotlinActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}