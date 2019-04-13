package com.example.knowledge.Stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String args[]) {
        List<String> list = Arrays.asList("aa", "bb", "cc");
        Stream<String> stream3 = list.stream().flatMap(l -> {
            String[] strings = l.split("");
            return Arrays.stream(strings);
        });
        stream3.forEach(System.out::println);
    }
}
