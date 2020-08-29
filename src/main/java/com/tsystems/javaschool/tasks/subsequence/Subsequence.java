package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;
import java.util.Objects;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {

        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }

        /*
         * Маркер для фиксации следующего индекса после найденного элемента в {List y}.
         * Если в {List y} будет найден элемент эквивалентный элементу в {List x}, тогда нас будут интересовать
         * только оставшиеся элементы в {List y} после найденного элемента, чтобы найти последовательность в таком
         * же порядке, что и в {List x}.
         */
        int marker = 0;

        label:
        for (int i = 0; i < x.size(); i++) {
            for (int j = marker; j < y.size(); j++) {
                if (Objects.equals(x.get(i), y.get(j))) {
                    marker = j + 1;
                    continue label;
                }
            }
            return false; // если не сработает метка label, то в {List y} нет элемента из {List x}, сразу false
        }
        return true;
    }
}