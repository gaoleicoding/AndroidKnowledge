package com.example.knowledge.java.thread;

import android.view.View;
import android.view.ViewGroup;

import com.example.knowledge.java.equal.Book;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadDemo {

    public static void main(String args[]) {
        ExecutorService pool2 = Executors.newCachedThreadPool();
        ExecutorService pool1 = Executors.newFixedThreadPool(2);
        ExecutorService pool3 = Executors.newSingleThreadExecutor();
        ExecutorService pool4 = Executors.newScheduledThreadPool(2);
        LinkedBlockingQueue workQueue = new LinkedBlockingQueue<>(10);

    }

}
