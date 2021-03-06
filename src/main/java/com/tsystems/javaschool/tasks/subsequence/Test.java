package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Test {
    public static void main(String[] args) {
        List x = Stream.of(1, 3, 5, 7, 9).collect(toList());
        List y = Stream.of(10, 1, 2, 3, 4, 5, 7, 9, 20).collect(toList());
        Subsequence subsequence = new Subsequence();
        System.out.println(subsequence.find(x,y));
    }
}
