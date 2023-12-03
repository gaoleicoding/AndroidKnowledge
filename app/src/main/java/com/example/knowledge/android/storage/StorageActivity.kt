package com.example.knowledge.android.storage

import android.content.SharedPreferences
import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.knowledge.R
import com.example.knowledge.activity.BaseActivity
import com.fifedu.lib_common_utils.log.LogUtils

class StorageActivity : BaseActivity() {
    lateinit var sharedPreferences: SharedPreferences
    val TAG = "StorageActivity"

    override fun getLayoutId(): Int {
        return R.layout.activity_storage
    }

    override fun initView() {
        mTitleTv.setText("Storage")
        findViewById<Button>(R.id.bt_put).setOnClickListener {
            putEncryptSP("test_key", "gaolei1201")
        }
        findViewById<Button>(R.id.bt_get).setOnClickListener {
            val result = getEncryptSP("test_key")
            findViewById<TextView>(R.id.tv_result).text = result
            LogUtils.e(TAG, "result:" + result)
        }
    }

    override fun initData() {
        initEncryptSP()
    }

    fun initEncryptSP() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
            sharedPreferences = EncryptedSharedPreferences.create(
                "encrypt_sp",
                masterKeyAlias,
                this,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
    }

    fun putEncryptSP(key: String, value: String) {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    fun getEncryptSP(key: String): String {
        return sharedPreferences.getString(key, "") as String
    }

}