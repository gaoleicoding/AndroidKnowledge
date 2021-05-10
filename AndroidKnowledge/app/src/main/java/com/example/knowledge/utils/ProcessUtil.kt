package com.example.knowledge.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.os.Binder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

object ProcessUtil {

    private const val TAG = "MyContentProvider"

    /**
     * 通过反射ActivityThread获取进程名，避免了ipc
     */
    val currentProcessNameByActivityThread: String?
        get() {
            var processName: String? = null
            try {
                val declaredMethod = Class.forName("android.app.ActivityThread", false, Application::class.java.classLoader)
                        .getDeclaredMethod("currentProcessName", *arrayOfNulls<Class<*>?>(0))
                declaredMethod.isAccessible = true
                val invoke = declaredMethod.invoke(null, *arrayOfNulls(0))
                if (invoke is String) {
                    processName = invoke
                }
            } catch (e: Throwable) {
            }
            return processName
        }

    fun testBinder(activity: Activity) {
        Log.d(TAG, "getCallingPid: " + Binder.getCallingPid())
        Log.d(TAG, "getCallingUid: " + Binder.getCallingUid())
        Log.d(TAG, "getProcessNanmeByPid: " + getProcessNanmeByPid(activity, Binder.getCallingPid()))
    }

    fun getProcessNanmeByPid(activity: Activity, pid: Int): String? {
        val mActivityManager = activity.getSystemService(AppCompatActivity.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in mActivityManager
                .runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        return null
    }
}