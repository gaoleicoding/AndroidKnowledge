package com.example.knowledge.utils

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.createDataStore
import com.example.knowledge.MessageEvent
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

/**
 *
 * 需要添加混淆：
-keepclassmembers class * extends androidx.datastore.preferences.protobuf.GeneratedMessageLite {
<fields>;
}
 */
class MessageEventDataStore private constructor() {

    object SettingsSerializer : Serializer<MessageEvent> {
        override fun readFrom(input: InputStream): MessageEvent {
            try {
                return MessageEvent.parseFrom(input)
            } catch (exception: InvalidProtocolBufferException) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override fun writeTo(t: MessageEvent, output: OutputStream) = t.writeTo(output)
        override val defaultValue: MessageEvent
            get() = MessageEvent.getDefaultInstance()

    }

    val messageEventDataStore: DataStore<MessageEvent>? by lazy {
        ContextProvider.appContext?.createDataStore(
                fileName = "message_event_datastore",
                serializer = SettingsSerializer
        )
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = MessageEventDataStore()
    }

    suspend fun getMessageEventType(): Int? {
        return messageEventDataStore?.data?.map {
            it.type
        }?.first()
    }

    suspend fun getMessageEventData(): String? {
        return messageEventDataStore?.data?.map {
            it.message
        }?.first()
    }

    suspend fun getMessageEvent(): MessageEvent? {
        return messageEventDataStore?.data?.map {
            it
        }?.first()
    }

    suspend fun putMessageEvent(message: String) {
        messageEventDataStore?.updateData {
            it.toBuilder()
                    .setType(it.type)
                    .setMessage(message)
                    .build()
        }
    }

    suspend fun putMessageEvent(type: Int) {
        messageEventDataStore?.updateData {
            it.toBuilder()
                    .setType(type)
                    .setMessage(it.message)
                    .build()
        }
    }

    suspend fun putMessageEvent(type: Int, message: String) {
        messageEventDataStore?.updateData {
            it.toBuilder()
                    .setType(type)
                    .setMessage(message)
                    .build()
        }
    }
}