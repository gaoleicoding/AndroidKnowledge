package com.example.knowledge.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PrefDataStoreUtil private constructor() {

    val mPrefDataStore: DataStore<Preferences>? by lazy {
        ContextProvider.appContext!!.createDataStore("user_info_datastore")
    }

    companion object {
        val instance = SingletonHolder.holder
        const val USER_NAME = "user_name"
        const val USER_PWD = "user_pwd"
    }

    private object SingletonHolder {
        val holder = PrefDataStoreUtil()
    }


    suspend fun putInt(key: String, value: Int) {
        val preKey = preferencesKey<Int>(key)
        mPrefDataStore!!.edit { mutablePreferences ->
            mutablePreferences[preKey] = value
        }

    }

    suspend fun getInt(key: String): Int {
        val preKey = preferencesKey<Int>(key)
        return mPrefDataStore!!.data
                .map { currentPreferences ->
                    // 不同于 Proto DataStore，这里不保证类型安全。
                    currentPreferences[preKey] ?: -1
                }.first()
    }

    suspend fun putString(key: String, value: String) {
        val preKey = preferencesKey<String>(key)
        mPrefDataStore!!.edit { mutablePreferences ->
            mutablePreferences[preKey] = value
        }

    }

    suspend fun getString(key: String): String {
        val preKey = preferencesKey<String>(key)

        return mPrefDataStore!!.data
                .map { currentPreferences ->
                    // 不同于 Proto DataStore，这里不保证类型安全。
                    currentPreferences[preKey] ?: ""
                }.first()
    }

    suspend inline fun <reified T : Any> prefDataStoreSave(key: String, value: T) = when (T::class) {
        String::class -> mPrefDataStore!!.edit { it[preferencesKey<T>(key)] = value }
        Int::class -> mPrefDataStore!!.edit { it[preferencesKey<T>(key)] = value }
        Boolean::class -> mPrefDataStore!!.edit { it[preferencesKey<T>(key)] = value }
        else -> throw IllegalArgumentException("Type not supported: ${T::class.java}")
    }

    suspend inline fun <reified T : Any> prefDataStoreRead(key: String) = when (T::class) {
        String::class -> mPrefDataStore!!.data.map {
            it[preferencesKey<T>(key)] ?: ""
        }.first() as T
        Int::class -> mPrefDataStore!!.data.map {
            it[preferencesKey<T>(key)] ?: 0
        }.first() as T
        Boolean::class -> mPrefDataStore!!.data.map {
            it[preferencesKey<T>(key)] ?: false
        }.first() as T
        else -> throw IllegalArgumentException("Type not supported: ${T::class.java}")
    }
}