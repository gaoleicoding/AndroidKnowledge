package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.utils.LogUtil

class SecondActivity : AppCompatActivity() {
    private val TAG = "SecondActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(TAG, "SecondActivity--onCreate()")
        setContentView(R.layout.activity_second)
        findViewById<View>(R.id.tv_to_third).setOnClickListener {
            startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
        }
    }

    fun onBack(view: View) {
        finish()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //must store the new intent unless getIntent() will return the old one
        setIntent(intent)
        getNewIntent()
        LogUtil.d(TAG, "SecondActivity--onNewIntent()")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        LogUtil.d(TAG, "SecondActivity--onWindowFocusChanged: " + hasFocus)
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
        LogUtil.d(TAG, "SecondActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(TAG, "SecondActivity--onResume()")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.d(TAG, "SecondActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d(TAG, "SecondActivity--onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.d(TAG, "SecondActivity--onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d(TAG, "SecondActivity--onDestroy()")
    }
}