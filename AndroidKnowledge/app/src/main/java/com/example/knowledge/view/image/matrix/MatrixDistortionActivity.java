package com.example.knowledge.view.image.matrix;

import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

public class MatrixDistortionActivity extends AppCompatActivity {

    ImageView imageView, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix2);
        imageView = (ImageView) findViewById(R.id.img);
        imageView.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix()));
        imageView2 = (ImageView) findViewById(R.id.img2);
        Bitmap originBmp = BitmapUtil.drawableToBitmap2(getResources().getDrawable(R.mipmap.girl));
        Bitmap bitmap = BitmapUtil.rotate(originBmp, 190, false);
        imageView2.setImageBitmap(bitmap);
    }
}
