package com.example.androidknowledge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.reactivestreams.Subscriber;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class LambdaActivity extends AppCompatActivity {

    TextView textView;
    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        textView.setOnClickListener(v -> Toast.makeText(this, "this is lambda", Toast.LENGTH_SHORT).show());

        class ThreadOne extends Thread {
            @Override
            public void run() {
                //处理逻辑
            }
        }
        new Thread(() -> {
            //处理逻辑
        }).start();

        Hello((a, b) -> {
            String result = a + b;
            return result;
        });

        //接口的匿名实现方法
        MyListenner myListenner = (a, b) -> {
            String result = a + b;
            return result;
        };
        String[] strArray = new String[]{"a", "b", "c"};
        List<String> names = Arrays.asList(strArray);
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });
        Collections.sort(names, (String a, String b) -> b.compareTo(a));
        Integer[] list = {1, 2, 3, 4, 5};

    }

    //自定义接口写法
    public interface MyListenner {
        String doSomething(String a, int b);
    }

    //接受MyListenner参数的方法
    public void Hello(MyListenner myListenner) {
        String a = "Hello Lambda";
        int b = 1024;
        String result = myListenner.doSomething(a, b);
        Log.i(TAG, "Hello: " + result);
    }

    public void rxjavaDemo() {


    }

}
