package com.example.knowledge;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class Demo2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Set<String> routerMap = null;

        getSharedPreferences("AROUTER_SP_CACHE_KEY", Context.MODE_PRIVATE).edit().putStringSet("AROUTER_SP_KEY_MAP", routerMap).apply();
    }
}