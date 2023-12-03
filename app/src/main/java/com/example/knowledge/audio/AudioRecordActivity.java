package com.example.knowledge.audio;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.audiofx.NoiseSuppressor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.knowledge.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AudioRecordActivity extends Activity {
    private final String TAG = "AudioRecordActivity";
    private boolean isRecording = false;
    private boolean isGetVoiceRun = false;
    private Object tmp = new Object();
    private Button thread_recorder;
    public String outputFilePath;
    private Object mLock = new Object();
    private TextView tv_record_volume;
    final int SAMPLE_RATE = 44100;
    int CHANNEL_CONFIG = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        outputFilePath = getExternalFilesDir("AudioRecord") + "/reverseme.pcm";
        thread_recorder = findViewById(R.id.thread_recorder);
        Button play = findViewById(R.id.play_bt);
        Button start = findViewById(R.id.start_bt);
        Button stop = findViewById(R.id.end_bt);
        tv_record_volume = findViewById(R.id.tv_record_volume);

        start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        record();
                    }
                });
                thread.start();
                findViewById(R.id.start_bt).setEnabled(false);
                findViewById(R.id.end_bt).setEnabled(true);
            }
        });


        stop.setEnabled(false);
        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                isRecording = false;
                isGetVoiceRun = false;
                findViewById(R.id.start_bt).setEnabled(true);
                findViewById(R.id.end_bt).setEnabled(false);
            }
        });

        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                play();
            }
        });


        thread_recorder.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AudioRecordThread rthread = new AudioRecordThread();
                rthread.start();
            }
        });

    }

    public void record() {

        File file = new File(outputFilePath);
//        int SAMPLE_RATE = 11025;
//        int CHANNEL_CONFIG = AudioFormat.CHANNEL_CONFIGURATION_MONO;
//        int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
        // Delete any previous recording.
        if (file.exists())
            file.delete();
        // Create the new file.
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create "
                    + file.toString());
        }
        try {
            // Create a DataOuputStream to write the audio data into the saved
            // file.
            OutputStream os = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            // Create a new AudioRecord object to record the audio.
            int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);

            AudioRecord audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.VOICE_COMMUNICATION, SAMPLE_RATE,
                    CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize);
//
//            NoiseSuppressor noiseSuppressor = NoiseSuppressor.create(audioRecord.getAudioSessionId());
//            noiseSuppressor.setEnabled(true);
            short[] buffer = new short[bufferSize];

            audioRecord.startRecording();
            isRecording = true;
            while (isRecording) {
                int readSize = audioRecord.read(buffer, 0, buffer.length);

                for (int i = 0; i < readSize; i++)
                    dos.writeShort(buffer[i]);

                isGetVoiceRun = true;
//                getNoiseLevel(audioRecord, bufferSize);
            }
            audioRecord.stop();
            dos.close();
        } catch (Exception e) {
            Log.e("AudioRecord", "Recording Failed:" + e.getMessage());
        }
    }

    public void play() {
        // Get the file we want to playback.
        File file = new File(outputFilePath);
        // Get the length of the audio stored in the file (16 bit so 2 bytes per
        // short)
        // and create a short array to store the recorded audio.
        int musicLength = (int) (file.length() / 2);
        short[] music = new short[musicLength];
        try {
            // Create a DataInputStream to read the audio data back from the
            // saved file.
            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            // Read the file into the music array.
            int i = 0;
            while (dis.available() > 0) {
                music[i] = dis.readShort();
                i++;
            }
            // Close the input streams.
            dis.close();
            // Create a new AudioTrack object using the same parameters as the
            // AudioRecord
            // object used to create the file.
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    SAMPLE_RATE, CHANNEL_CONFIG,
                    AUDIO_FORMAT, musicLength * 2,
                    AudioTrack.MODE_STREAM);
            // Start playback
            audioTrack.play();
            // Write the music buffer to the AudioTrack object
            audioTrack.write(music, 0, musicLength);
            audioTrack.stop();
        } catch (Throwable t) {
            Log.e("AudioTrack", "Playback Failed");
        }
    }

    public void getNoiseLevel(AudioRecord mAudioRecord, int BUFFER_SIZE) {

        short[] buffer = new short[BUFFER_SIZE];
//        while (isGetVoiceRun) {
//            //r是实际读取的数据长度，一般而言r会小于buffersize
//            int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
//            long v = 0;
//            // 将 buffer 内容取出，进行平方和运算
//            for (short value : buffer) {
//                v += value * value;
//            }
//            // 平方和除以数据总长度，得到音量大小。
//            double mean = v / (double) r;
//            double volume = 10 * Math.log10(mean);
//            Log.d(TAG, "分贝值 = " + volume + "dB");
//            tv_record_volume.setText("分贝值 = " + volume + "dB");
//            synchronized (mLock) {
//                try {
//                    mLock.wait(100); // 一秒十次
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        mAudioRecord.stop();
//        mAudioRecord.release();


        while (isGetVoiceRun) {
            int readSize = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
            long sum = 0;
            for (int i = 0; i < readSize; i++) {
                sum += Math.abs(buffer[i]);
            }
//            synchronized (mLock) {
//                try {
//                    mLock.wait(100); // 一秒十次
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            if (readSize > 0) {
                //获取音频振幅值
                long amplitude = sum / readSize;
                // 处理音量大小
                int volume = (int) (20 * Math.log10(amplitude));
                Log.d(TAG, "分贝值 = " + volume + "dB");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv_record_volume.setText("分贝值 = " + volume + "dB");
//                    }
//                });

            }
//            if (readSize > 0) {
//                int amplitude = (int) (sum / readSize);
//                // 处理音量大小
//                int volume = (int) (20 * Math.log10(amplitude));
//            }
//                mAudioRecord.stop();
//                mAudioRecord.release();
//            }
        }
    }
}