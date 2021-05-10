package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    private val TAG = "SecondActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "SecondActivity--onCreate()")
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
        Log.i(TAG, "SecondActivity--onNewIntent()")
    }

    private fun getNewIntent() {
        //use the data received here
        val intent = intent
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "SecondActivity--onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "SecondActivity--onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "SecondActivity--onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "SecondActivity--onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "SecondActivity--onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "SecondActivity--onDestroy()")
    }
}