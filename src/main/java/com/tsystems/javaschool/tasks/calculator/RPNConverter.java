package com.tsystems.javaschool.tasks.calculator;

/*
 * Данный класс конвертирует валидное выражение в обратную польскую нотацию
 *(Reverse Polish notation)
 */

import java.util.*;


public class RPNConverter {

    public String convert(String input) {
        String result = "";
        Queue<Character> operatorsQueue = Collections.asLifoQueue(new ArrayDeque<>());


        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            int priority = getPriority(currentChar);

            /* Если символ - цифра или точка, то сразу добавляем его к результату */
            if (priority == 0) {
                result += currentChar;
                continue;
            }

            /* Если символ - скобка, добавляем её в очередь */
            if (priority == 1) {
                operatorsQueue.offer(currentChar);
                continue;
            }

            /*
             *  Если символ - арифметическая операция, то сравниваем её приоритет с
             *  символами в очереди. Если в очереди окажется операция с бОльшим или равным приоритетом,
             *  то добавляем найденный оператор в результат, а после добавляем наш символ в очередь.
             *  Например, если input.charAt(i) = '+', тогда мы смотрим в очередь и если в ней есть
             *  какой-нибудь другой оператор, мы добавим его в результат.
             */
            if (priority > 1) {
                result += " "; // отделяем пробелом числа друг от друга
                while (!operatorsQueue.isEmpty()) {
                    if (getPriority(operatorsQueue.element()) >= priority) {
                        result += operatorsQueue.remove();
                    } else break;
                }
                operatorsQueue.offer(currentChar);
                continue;
            }

            /*
             * Если символ - это закрывающая скобка, то начинаем поиск открывающей скобки,
             * попутно вытаскивая из очереди элементы и добавляем их к результату
             */
            if (priority == -1) {
                result += " ";
                while (getPriority(operatorsQueue.element()) != 1) {
                    result += operatorsQueue.remove();
                }
                operatorsQueue.remove(); // удаляем встретившуюся '('
            }
        }

        // Вытаскиваем оставшиеся операторы из очереди
        while (!operatorsQueue.isEmpty()) {
            result += operatorsQueue.remove();
        }
        return result;
    }


    /**
     * Присваиваем приоритеты арифметическим операциям, скобкам и остальным символам.
     *
     * @param ch characters from expression to evaluate
     * @return int value of priority for specified character
     */

    public static int getPriority(char ch) {
        if (ch == '*' || ch == '/') {
            return 3;
        }
        if (ch == '+' || ch == '-') {
            return 2;
        }
        if (ch == '(') {
            return 1;
        }
        if (ch == ')') {
            return -1;
        }

        return 0; // ноль для цифр 0-9 и точек
    }

}
