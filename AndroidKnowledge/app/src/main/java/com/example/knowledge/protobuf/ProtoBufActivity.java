package com.example.knowledge.protobuf;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.UserProto;
import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufActivity extends AppCompatActivity {

    private static final String TAG = "ProtoBufActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserProto.User.Builder builder = UserProto.User.newBuilder();
        builder.setName("CSDN");
        builder.setAge(366);

        UserProto.User user = builder.build();
        Log.d(TAG, "user.getName() = " + user.getName());
        Log.d(TAG, "user.getAge() = " + user.getAge() + "");

        // 序列化
        byte[] bytes = user.toByteArray();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bytes) {
            stringBuffer.append(b + " ");
        }
        // 打印序列化结果
        Log.d(TAG, "result = " + stringBuffer.toString());
        // 反序列化
        try {
            UserProto.User deserializeUser = UserProto.User.parseFrom(bytes);
            Log.d(TAG, "deserializeUser = " + deserializeUser.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}