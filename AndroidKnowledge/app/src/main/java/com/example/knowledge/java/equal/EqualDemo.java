package com.example.knowledge.java.equal;

import java.util.ArrayList;
import java.util.List;

public class EqualDemo {

    public static void main(String args[]) {
        List<Book> bookList = new ArrayList<>();
        Book bookAndroid = new Book(1, "Android", "android");
        bookList.add(bookAndroid);
        Book bookAndroid2 = new Book(1, "Android", "android");
        Book bookIOS = new Book(2, "IOS", "ios");
        bookList.add(bookIOS);
        Book h5IOS = new Book(3, "H5", "h5");
        bookList.add(h5IOS);
        boolean isEqual = bookAndroid.equals(bookAndroid2);
        boolean isContain = bookList.contains(bookAndroid2);

        System.out.println("isEqual:" + isEqual);
        System.out.println("isContain:" + isContain);
    }
}
