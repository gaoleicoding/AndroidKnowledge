package com.example.knowledge.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import com.fifedu.lib_common_utils.ContextProvider;

import java.io.File;
import java.io.IOException;

public class MediaRecorderUtil {
    public MediaRecorder mediaRecorder;
    public String outputFilePath;

    private MediaRecorderUtil() {
        outputFilePath = ContextProvider.getAppContext().getExternalFilesDir("audio") + "/recorded_audio.3gp";
    }

    public static MediaRecorderUtil instance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private final static MediaRecorderUtil instance = new MediaRecorderUtil();
    }

    public void startRecording() {
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
            // 设置MediaRecorder的音频源为MediaRecorder.AudioSource.REMOTE_SUBMIX，这样就可以只录制手机播放的声音，而不录制话筒的声音。
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            mediaRecorder.setAudioSamplingRate(44100); // 设置采样率，可以调整为其他值
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            File outputFile = new File(outputFilePath);
            if (outputFile.exists()) {
                outputFile.delete();
            }
            mediaRecorder.setOutputFile(outputFilePath);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }



    public void getAudioFocus(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);


        AudioManager.OnAudioFocusChangeListener afChangeListener =

                new AudioManager.OnAudioFocusChangeListener() {

                    public void onAudioFocusChange(int focusChange) {

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
                };

        int result = audioManager.requestAudioFocus(afChangeListener,
                // 使用音频流
                AudioManager.STREAM_SYSTEM,
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // 音频焦点请求成功，执行相应逻辑
            MediaRecorderUtil.instance().startRecording();

        } else {
            // 音频焦点请求失败，执行相应逻辑
            MediaRecorderUtil.instance().startRecording();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getAudioFocus2(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes)
                .setAcceptsDelayedFocusGain(true)
                .setOnAudioFocusChangeListener(afChangeListener, handler)
                .build();
        MediaPlayer mediaPlayer = new MediaPlayer();
        final Object focusLock = new Object();

        boolean playbackDelayed = false;
        boolean playbackNowAuthorized = false;

        // ...
        int res = audioManager.requestAudioFocus(focusRequest);
        synchronized (focusLock) {
            if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
                playbackNowAuthorized = false;
                MediaRecorderUtil.instance().startRecording();
            } else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                playbackNowAuthorized = true;
                MediaRecorderUtil.instance().startRecording();
            } else if (res == AudioManager.AUDIOFOCUS_REQUEST_DELAYED) {
                playbackDelayed = true;
                playbackNowAuthorized = false;
                MediaRecorderUtil.instance().startRecording();
            }
        }

    }

    private Handler handler = new Handler();
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        // Pause playback
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, keep playing
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                    }
                }
            };

}