package com.example.knowledge.annotation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;
import com.example.knowledge.java.deepcopy.Student;

/*
       @Nullable和NonNull(NotNull)
       这些注解是用来标注方法是否能传入null值，
       如果可以传入NUll值，则标记为nullbale，如果不可以则标注为Nonnull.
       在我们做了一些不安全严谨的编码操作的时候,这些注释会给我们一些警告。
        */
public class AnnotationActivity extends AppCompatActivity {

    String TAG = "AnnotationActivity";
    private TextView tvAnnotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        tvAnnotation = findViewById(R.id.tv_annotation);
        set(null);
        get("gaolei");
    }

    private void set(@NonNull Student student) {
        tvAnnotation.setText("gaolei");
    }

    private @NonNull Student get( String name) {
        return null;
    }
}
