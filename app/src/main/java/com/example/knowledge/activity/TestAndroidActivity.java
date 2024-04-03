package com.example.knowledge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knowledge.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestAndroidActivity extends AppCompatActivity {

    private final String TAG = "TestAndroidActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView title = findViewById(R.id.title_text);
        title.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        int mHeaderViewHeight = title.getHeight();
                        title.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        forEach();
        forEach2();
    }

    public void iterate() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("B");
        list.add("C");
        list.add("D");

        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
            int currentIndex = iterator.nextIndex();
            int previousIndex = iterator.previousIndex();
            Log.d(TAG, "currentIndex:" + currentIndex);
            Log.d(TAG, "previousIndex:" + previousIndex);
            iterator.next();
        }
    }

    public void forEach() {
        List<String> myList = new ArrayList<String>();

        myList.add("1");
        myList.add("2");
        myList.add("3");
        myList.add("4");
        myList.add("5");
        // 1 使用Iterator提供的remove方法，用于删除当前元素
        for (Iterator<String> it = myList.iterator(); it.hasNext(); ) {
            String value = it.next();
            if (value.equals("3")) {
                it.remove();  // ok
            }
        }
        System.out.println("List Value:" + myList.toString());

        // 2 建一个集合，记录需要删除的元素，之后统一删除
        List<String> templist = new ArrayList<String>();
        for (String value : myList) {
            if (value.equals("3")) {
                templist.remove(value);
            }
        }
        // 可以查看removeAll源码，其中使用Iterator进行遍历
        myList.removeAll(templist);
        System.out.println("List Value:" + myList.toString());


        // 4. 不使用Iterator进行遍历，需要注意的是自己保证索引正常
        for (int i = 0; i < myList.size(); i++) {
            String value = myList.get(i);
            System.out.println("List Value:" + value);
            if (value.equals("3")) {
                myList.remove(i);  // ok
            }
        }
        System.out.println("List Value 4:" + myList.toString());
    }

    private void forEach2() {
        // 3. 使用线程安全CopyOnWriteArrayList进行删除操作
        List<String> myList = new CopyOnWriteArrayList<>();
        myList.add("1");
        myList.add("2");
        myList.add("3");
        myList.add("4");
        myList.add("5");

        Iterator<String> it = myList.iterator();

        while (it.hasNext()) {
            String value = it.next();
            if (value.equals("3")) {
                myList.remove("4");
                myList.add("6");
                myList.add("7");
            }
        }
        System.out.println("List Value:" + myList.toString());
    }
}