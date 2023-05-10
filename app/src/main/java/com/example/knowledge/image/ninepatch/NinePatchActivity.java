package com.example.knowledge.image.ninepatch;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

import java.io.InputStream;

public class NinePatchActivity extends AppCompatActivity {

    private static final String TAG = "NinePatchActivity";

    private LinearLayout llReceive, llSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninepatch);

        llReceive = findViewById(R.id.ll_receive);
        llSend = findViewById(R.id.ll_send);


        llReceive.setBackground(getNinePttchDrawable("ecgnc_background_chat_bubble_left.9.png"));
        llSend.setBackground(getNinePttchDrawable("ecgnc_background_chat_bubble_right.9.png"));

    }

    protected Drawable getNinePttchDrawable(String url) {
        NinePatchDrawable drawable = null;
        try {
            AssetManager assets = getApplicationContext().getAssets();
            InputStream is = assets.open(url);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            drawable = NinePatchChunk.create9PatchDrawable(this, bitmap, null);
        } catch (Exception e) {
            Log.d(TAG, "e:" + e.getMessage());
        }
        return drawable;
    }

}
