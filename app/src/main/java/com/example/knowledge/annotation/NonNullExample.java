package com.example.knowledge.annotation;

import androidx.annotation.NonNull;

public class NonNullExample {

    private String name;

    // 在构造器上使用@NonNull
    public NonNullExample(@NonNull String name) {
        this.name = name;
    }

    // 在普通方法上使用@NonNull
    public static void example(@NonNull String str) {
        System.out.println(str);
    }

    public static void main(String[] args) {
        // 异常1
        NonNullExample example = new NonNullExample(null);
        // 异常2
        example(null);
    }
}
