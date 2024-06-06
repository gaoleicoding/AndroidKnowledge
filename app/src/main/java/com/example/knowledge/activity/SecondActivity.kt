package com.example.knowledge.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.R
import com.fifedu.lib_common_utils.log.LogUtils

class SecondActivity : AppCompatActivity() {
    private val TAG = "SecondActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d(TAG, "SecondActivity--onCreate()")
        setContentView(R.layout.activity_second)
        findViewById<View>(R.id.tv_to_third).setOnClickListener {
            startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //must store the new intent unless getIntent() will return the old one
        setIntent(intent)
        getNewIntent()
        LogUtils.d(TAG, "SecondActivity--onNewIntent()")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        LogUtils.d(TAG, "SecondActivity--onWindowFocusChanged: " + hasFocus)
        if (hasFocus) {

        } else {

        }
        super.onWindowFocusChanged(hasFocus)
    }

    private fun getNewIntent() {
        //use the data received here
        val intent = intent
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d(TAG, "SecondActivity--onStart()")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtils.d(TAG, "SecondActivity--onRestart()")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(TAG, "SecondActivity--onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(TAG, "SecondActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d(TAG, "SecondActivity--onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG, "SecondActivity--onDestroy()")
    }
}