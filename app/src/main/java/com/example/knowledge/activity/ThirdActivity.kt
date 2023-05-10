package com.example.knowledge.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.R
import com.example.knowledge.utils.LogUtil

class ThirdActivity : AppCompatActivity() {
    private val TAG = "ThirdActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(TAG, "ThirdActivity--onCreate()")
        setContentView(R.layout.activity_third)
        findViewById<View>(R.id.tv_to_second).setOnClickListener {
            startActivity(Intent(this@ThirdActivity, SecondActivity::class.java))
        }
        findViewById<View>(R.id.tv_to_third).setOnClickListener {
            startActivity(Intent(this@ThirdActivity, ThirdActivity::class.java))
        }
    }

    fun onBack(view: View) {
        finish()
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d(TAG, "ThirdActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(TAG, "ThirdActivity--onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d(TAG, "ThirdActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d(TAG, "ThirdActivity--onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.d(TAG, "ThirdActivity--onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "ThirdActivity--onDestroy()")
    }
}