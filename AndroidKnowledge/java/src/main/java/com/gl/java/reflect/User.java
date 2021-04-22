package com.gl.java.reflect;

import java.util.Arrays;

class User {
    public static void method(String... strings) {
        System.out.println(Arrays.toString(strings));
    }

    private void method(int... ints) {
        System.out.println(Arrays.toString(ints));
    }
}