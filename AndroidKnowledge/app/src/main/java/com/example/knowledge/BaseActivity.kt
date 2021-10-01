package com.example.knowledge

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //7.0以上TRANSLUCENT_STATUS时部分手机状态栏有灰色遮罩，去掉它
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val decorView = window.decorView
            /* getWindow().getDecorView()被调用后, getWindow().getAttributes().flags才有值。
             * getDecorView()正常调用时机：
             * ①setContentView()中；
             * ②onCreate()和onPostCreate()之间；
             * ③④⑤等等...
             * 所以下面的代码应当放在setContentView()或onPostCreate()中，
             * 但有的activity没有setContentView()，有的activity会在onCreate()中finish()，
             * 所以此处在onCreate()中手动调用一下getDecorView()。
             */if (window.attributes.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS != 0) {
                try {
                    val field = decorView.javaClass.getDeclaredField("mSemiTransparentStatusBarColor")
                    field.isAccessible = true
                    field.setInt(decorView, Color.TRANSPARENT)
                } catch (ignored: Exception) {
                }
            }
        }
    }

    fun onBack(view: View) {
        finish()
    }

}