package com.example.knowledge.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import androidx.collection.SimpleArrayMap;

import com.example.knowledge.AppApplication;

import java.lang.reflect.Field;


public class TypeFaceUtil {
    private static final String TAG = "TypeFaceUtils";
    //字体地址，一般放置在assets/fonts目录
    public static final String fontPath_ARIAL = "fonts/ARIAL.TTF";
    public static final String fontPath_DroidSansFallbackBd = "fonts/DroidSansFallbackBd.ttf";
    public static final String fontPath_DroidSansFallback = "fonts/DroidSansFallback.ttf";

    private static final SimpleArrayMap<String, Typeface> TYPEFACE_CACHE = new SimpleArrayMap<>();

    public static Typeface get(Context context, String name) {
        synchronized (TYPEFACE_CACHE) {
            if (!TYPEFACE_CACHE.containsKey(name)) {

                try {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), name);
                    TYPEFACE_CACHE.put(name, t);
                } catch (Exception e) {
                    LogUtil.e(TAG, "Could not get typeface '" + name
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return TYPEFACE_CACHE.get(name);
        }
    }

    public static void setTypeface(TextView textView, String fontPath) {
        try {
            Typeface typeface = get(AppApplication.context, fontPath);
            textView.setTypeface(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void replaceSystemDefaultFont(Context context, String fontPath) {
        Typeface typeface = get(context, fontPath);
        //SANS_SERIF
        replaceTypefaceField("MONOSPACE", typeface);
    }

    //关键--》通过修改字体为自定义的字体达到修改app默认字体的目的
    private static void replaceTypefaceField(String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(null, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
