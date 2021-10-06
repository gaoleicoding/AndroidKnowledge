package com.example.knowledge.image.maskfilter;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BlurMaskFilterView extends View {
    public BlurMaskFilterView(Context context) {
        this(context, null, 0);
    }

    public BlurMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlurMaskFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        // Paint的setMaskFilter不支持硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, paint);

        paint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL));
        canvas.drawRect(100, 100, 250, 250, paint);

        paint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));
        canvas.drawRect(100, 350, 250, 500, paint);

        paint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.OUTER));
        canvas.drawRect(100, 600, 250, 750, paint);

        paint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.INNER));
        canvas.drawRect(100, 850, 250, 1000, paint);
    }
}