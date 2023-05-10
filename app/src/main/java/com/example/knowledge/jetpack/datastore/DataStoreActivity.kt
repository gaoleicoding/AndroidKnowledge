package com.example.knowledge.jetpack.datastore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.knowledge.R
import com.example.knowledge.UserInfo
import com.example.knowledge.utils.MessageEventDataStore
import com.example.knowledge.utils.PrefDataStoreUtil
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.launch
import java.io.*

class DataStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datastore)
        testProtoBuf()
        saveProtoBufToFile()
        readProtoBufFromFile()
        testPrefDataStore()
        testProtoDataStore()
    }

    private fun testPrefDataStore() {
        lifecycleScope.launch() {
            PrefDataStoreUtil.instance.putString(PrefDataStoreUtil.USER_NAME, "gaolei")

        }
        lifecycleScope.launch {
            Log.d(TAG, "PrefDataStore -getString: " + PrefDataStoreUtil.instance.getString(PrefDataStoreUtil.USER_NAME))
        }
    }

    private fun testProtoDataStore() {
        lifecycleScope.launch() {
            MessageEventDataStore.instance.putMessageEvent(1, "New Message")

        }
        lifecycleScope.launch {
            Log.d(TAG, "ProtoDataStore -getMessage: " + MessageEventDataStore.instance.getMessageEvent().toString())
        }
    }

    private fun testProtoBuf() {
        val builder = UserInfo.newBuilder()
        builder.name = "zhao"
        builder.age = 25
        val user = builder.build()
        Log.d(TAG, "testProtoBuf-user = " + user.toString())

        // 序列化
        val bytes = user.toByteArray()
        val stringBuffer = StringBuffer()
        for (b in bytes) {
            stringBuffer.append("$b ")
        }
        // 打印序列化结果
        Log.d(TAG, "testProtoBuf-result = $stringBuffer")
        // 反序列化
        try {
            val deserializeUser = UserInfo.parseFrom(bytes)
            Log.d(TAG, "testProtoBuf-deserializeUser = $deserializeUser")
        } catch (e: InvalidProtocolBufferException) {
            e.printStackTrace()
        }

    }

    /*
    * proto可以往外写，使用writeTo(OutputStream)方法，
    * 可以是本地文件流，也可以是网络流。这里写入文件流
    *
    **/
    fun saveProtoBufToFile() {
        var rootDir: String = filesDir.absolutePath + "/protobuf"
        val dirFile = File(rootDir)
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }
        val file = File(rootDir, "user")
        try {
            val builder = UserInfo.newBuilder()
            builder.name = "Zhang"
            builder.age = 16
            val user = builder.build()
            Log.d(TAG, "saveProtoBufToFile-userInfo = ${user}")
            val outputStream = FileOutputStream(file)
            user.writeTo(outputStream)
            outputStream.close()
        } catch (e: IOException) {
            Log.e(TAG, "e: ${e.message}")
        }
    }

    fun readProtoBufFromFile() {
        val dir: File = filesDir
        val file = File(dir.absolutePath + "/protobuf", "user")
        try {
            val inputStream = FileInputStream(file)
            val out = ByteArrayOutputStream()
            val data = ByteArray(1024)
            var len = -1
            while (inputStream.read(data).also({ len = it }) != -1) {
                out.write(data, 0, len)
                out.flush()
            }
            val userInfo: UserInfo = UserInfo.parseFrom(out.toByteArray())
            out.close()
            // 打印序列化结果
            Log.d(TAG, "readProtoBufFromFile-userInfo = ${userInfo}")
        } catch (e: Exception) {
            Log.e(TAG, "e: ${e.message}")
        }
    }


    companion object {
        private const val TAG = "DataStoreActivity"

    }
}