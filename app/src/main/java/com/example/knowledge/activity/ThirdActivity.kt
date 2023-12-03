package com.example.knowledge.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.R
import com.fifedu.lib_common_utils.log.LogUtils

class ThirdActivity : AppCompatActivity() {
    private val TAG = "ThirdActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d(TAG, "ThirdActivity--onCreate()")
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
        LogUtils.d(TAG, "ThirdActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG, "ThirdActivity--onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG, "ThirdActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG, "ThirdActivity--onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.d(TAG, "ThirdActivity--onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG, "ThirdActivity--onDestroy()")
    }
}