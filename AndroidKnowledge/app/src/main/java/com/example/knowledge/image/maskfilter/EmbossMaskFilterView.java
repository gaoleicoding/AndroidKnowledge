package com.example.knowledge.image.maskfilter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class EmbossMaskFilterView extends View {
    public EmbossMaskFilterView(Context context) {
        this(context, null, 0);
    }

    public EmbossMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmbossMaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        setLayerType(View.LAYER_TYPE_SOFTWARE, paint);


        float[] direction = new float[]{20, 20, 20};
        float light = 0.5f;
        float specular = 1;
        float blur = 10;
        paint.setMaskFilter(new EmbossMaskFilter(direction, light, specular, blur));
        paint.setTextSize(100);
        paint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        canvas.drawText("Hello World", 50, 200, paint);
    }
}