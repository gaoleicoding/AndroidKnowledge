package com.example.knowledge.audio

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.audiofx.AcousticEchoCanceler
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * 音频播放
 */
@SuppressLint("StaticFieldLeak")
object MediaPlayerUtil {

    private var mContext: Context? = null
    var mPlayer: MediaPlayer? = null

    var isPause = false

    private var isPlaying = false

    fun isPlaying(): Boolean {
        return isPlaying
    }

    fun initMedia(mContext: Context, rawRes: Int) {
        mPlayer = MediaPlayer.create(mContext, rawRes)
        mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun initMedia(mContext: Context, fad: AssetFileDescriptor) {
        mPlayer = MediaPlayer()
        mPlayer!!.setDataSource(fad)
    }

    fun initMedia(mContext: Context, path: String) {
        try {
            mPlayer = MediaPlayer()
            //设置音频降噪
            if (AcousticEchoCanceler.isAvailable()) {
                val echoCancler: AcousticEchoCanceler =
                    AcousticEchoCanceler.create(mPlayer!!.audioSessionId)
                echoCancler.setEnabled(true)
            }
            mPlayer!!.setAudioStreamType(AudioManager.STREAM_VOICE_CALL)
            mPlayer!!.setDataSource(path)

        } catch (e: Exception) {
        }
    }

    fun initUriMedia(mContext: Context, uriPath: String) {
        mPlayer = MediaPlayer()
        mPlayer!!.setDataSource(mContext!!, Uri.parse(uriPath))
        mPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
    }

    fun setMediaListener(listener: MediaPlayerListener) {
        mPlayer!!.setOnCompletionListener {
            isPlaying = false
            listener.finish()
        }

        mPlayer!!.setOnErrorListener { mp, what, extra ->
            isPlaying = false
            listener.onErr(what)
            false
        }
        mPlayer!!.setOnPreparedListener {
            listener.prepare()
        }
    }

    fun playMedia(isPrepare: Boolean) {
        if (mPlayer != null && !mPlayer!!.isPlaying) {
            if (isPrepare) {
                mPlayer!!.prepare()
            }
            // 开始播放音频
            mPlayer!!.start()
        }
    }

    fun prepare() {
        if (mPlayer != null && !mPlayer!!.isPlaying) {
            mPlayer!!.prepare()
        }
    }

    fun prepareAsync() {
        if (mPlayer != null && !mPlayer!!.isPlaying) {
            mPlayer!!.prepareAsync()
        }
    }

    fun pause() {
        if (mPlayer != null && mPlayer!!.isPlaying) {
            mPlayer!!.pause()
            isPause = true
        }
    }

    // 继续
    fun resume() {
        if (mPlayer != null && isPause) {
            mPlayer!!.start()
            isPause = false
        }
    }

    fun release() {
        if (mPlayer != null) {
            try {
                mPlayer!!.release()
            } catch (e: Exception) {
            }
            mPlayer = null
        }
        isPlaying = false
    }

}

interface MediaPlayerListener {
    fun finish()
    fun onErr(what: Int)
    fun prepare() {
    }

}
