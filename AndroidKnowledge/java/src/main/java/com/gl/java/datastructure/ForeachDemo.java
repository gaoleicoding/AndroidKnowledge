package com.gl.java.datastructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForeachDemo {

    public static void main(String[] args) {
        foreach();
    }

    public static void foreach() {
        List<Integer> list = new ArrayList<>();
        for (Integer i : list) {
            System.out.println(i);
        }
    }

    public static void foreachAfterCompile() {
        ArrayList list = new ArrayList();
        Iterator var10 = list.iterator();
        Integer var11;
        while (var10.hasNext()) {
            var11 = (Integer) var10.next();
            System.out.println(var11);
        }
    }
}
