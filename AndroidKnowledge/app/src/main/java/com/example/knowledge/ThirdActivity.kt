package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {
    private val TAG = "ThirdActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "ThirdActivity--onCreate()")
        setContentView(R.layout.activity_third)
        findViewById<View>(R.id.title_text).setOnClickListener {
            startActivity(Intent(this@ThirdActivity, SecondActivity::class.java))
        }
    }

    fun onBack(view: View) {
        finish()
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "ThirdActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "ThirdActivity--onResumed()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "ThirdActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "ThirdActivity--onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "ThirdActivity--onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "ThirdActivity--onDestroy()")
    }
}