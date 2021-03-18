package com.gl.java.reflect;

import java.lang.reflect.Method;

public class RefTest {
    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("com.zy.java.RefTest");
            Object refTest = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("refMethod");
            method.invoke(refTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refMethod() {
    }
}