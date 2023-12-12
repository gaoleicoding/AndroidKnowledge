package com.example.knowledge.audio

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.R
import com.example.knowledge.databinding.ActivityAudioBinding
import com.fifedu.lib_common_utils.permission.PermissionCallBack
import com.fifedu.lib_common_utils.permission.PermissionUtils


class AudioActivity : AppCompatActivity() {
    var binding: ActivityAudioBinding? = null
    var path = "https://downsc.chinaz.net/Files/DownLoad/sound1/202103/s1024.mp3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.tvPlaySong.setOnClickListener {
            playRawAudio()
        }
        binding!!.tvStopSong.setOnClickListener {
            if (!MediaPlayerUtil.isPause) {
                MediaPlayerUtil.pause()
                binding!!.tvStopSong.text = "继续歌曲"
            } else {
                MediaPlayerUtil.resume()
                binding!!.tvStopSong.text = "停止歌曲"
            }
        }
        binding!!.tvRecordStart.setOnClickListener {
            PermissionUtils.requestPermissions(
                this@AudioActivity,
                false,
                object : PermissionCallBack() {
                    override fun onGranted() {
//                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                            AudioRecordUtil.instance().getAudioFocus(this@AudioActivity)
//                        } else {
//                            AudioRecordUtil.instance().getAudioFocus2(this@AudioActivity)
//                        }
                        MediaRecorderUtil.instance().startRecording()
                        updateMicStatus()
                    }

                    override fun onDenied() {
                    }
                },
                *PermissionUtils.PERMISSIONS_RECORD
            )
        }
        binding!!.tvRecordStop.setOnClickListener {
            MediaRecorderUtil.instance().stopRecording()
            mHandler.removeCallbacks(mUpdateMicStatusTimer)
        }
        binding!!.tvRecordPlay.setOnClickListener {
            playStorageAudio()
        }
    }

    private fun playRawAudio() {
        MediaPlayerUtil.initMedia(this, R.raw.longest_movie)
        MediaPlayerUtil.setMediaListener(object : MediaPlayerListener {
            override fun onErr(messageCode: Int) {
            }

            override fun finish() {
            }

            override fun prepare() {
            }
        })

        MediaPlayerUtil.playMedia(false)
    }

    private fun playStorageAudio() {
        MediaPlayerUtil.initMedia(this, MediaRecorderUtil.instance().outputFilePath)
        MediaPlayerUtil.setMediaListener(object : MediaPlayerListener {
            override fun onErr(messageCode: Int) {
            }

            override fun finish() {
            }

            override fun prepare() {
            }
        })

        MediaPlayerUtil.playMedia(true)
    }

    fun getAudioFocus(context: Context) {
        val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager
        val afChangeListener =
            OnAudioFocusChangeListener { focusChange ->
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                    // 永久失去音频焦点

                    // 立即停止播放
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {

                    // 暂时性失去焦点

                    // 播放暂停
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                    // 暂时性失去焦点

                    // 声音变低，继续播放
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                    // 获取到音频焦点

                    // 声音恢复到正常音量，开始播放
                }
            }
        val result = audioManager.requestAudioFocus(
            afChangeListener,  // 使用音频流
            AudioManager.STREAM_SYSTEM,
            AudioManager.AUDIOFOCUS_GAIN
        )
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // 音频焦点请求成功，执行相应逻辑
            MediaRecorderUtil.instance().startRecording()
        } else {
            // 音频焦点请求失败，执行相应逻辑
            MediaRecorderUtil.instance().startRecording()
        }
    }

    private val mHandler = Handler()

    fun updateMicStatus() {
        if (MediaRecorderUtil.instance().mediaRecorder != null) {
            val ratio: Double =
                MediaRecorderUtil.instance().mediaRecorder.getMaxAmplitude()
                    .toDouble() / 1 // 参考振幅为 1
            var db = 0.0 // 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio)
            }
            Log.d("AudioRecordUtil", "分贝值 = " + db + "dB")
            binding!!.tvRecordVolume.text = "分贝值 = " + db + "dB"
            mHandler.postDelayed(mUpdateMicStatusTimer, 100) // 间隔取样时间为100秒
        }
    }

    private val mUpdateMicStatusTimer = Runnable { updateMicStatus() }

}