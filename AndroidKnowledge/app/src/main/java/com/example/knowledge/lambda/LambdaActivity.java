package com.example.knowledge.lambda;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaActivity extends AppCompatActivity {

    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lambda);


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
