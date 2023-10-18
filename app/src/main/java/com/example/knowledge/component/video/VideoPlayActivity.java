package com.example.knowledge.component.video;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

public class VideoPlayActivity extends AppCompatActivity {
    private ConditionVideoView videoView;
    String videoUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoview);
        videoView = findViewById(R.id.videoView);

        videoView.setVideoPath(videoUrl);
        /**
         * 控制视频的播放 主要通过MediaController控制视频的播放
         */
        //创建MediaController对象
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController); //让videoView 和 MediaController相关联
        videoView.setFocusable(true); //让VideoView获得焦点
        videoView.start(); //开始播放视频
        //给videoView添加完成事件监听器，监听视频是否播放完毕
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(VideoPlayActivity.this, "该视频播放完毕！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}