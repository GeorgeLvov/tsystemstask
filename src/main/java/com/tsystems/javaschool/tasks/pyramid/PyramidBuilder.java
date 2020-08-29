package com.tsystems.javaschool.tasks.pyramid;


import java.util.*;

public class PyramidBuilder {

    public static void main(String[] args) {

    }
    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public int[][] buildPyramid(List<Integer> inputNumbers) {

        if (inputNumbers == null || inputNumbers.contains(null)) {
            throw new CannotBuildPyramidException();
        }

        int pyramidHeight = getPyramidHeight(inputNumbers);

        if (pyramidHeight == -1) {
            throw new CannotBuildPyramidException();
        }

        // Кол-во элементов из листа в основании пирамиды равно последнему элементу арифметической прогрессии + разделительные нули
        int pyramidBaseLength = pyramidHeight + (pyramidHeight - 1);

        int[][] pyramid = new int[pyramidHeight][pyramidBaseLength];

        Collections.sort(inputNumbers);

        Deque<Integer> numbers = new ArrayDeque<>(inputNumbers);

        /* Верхний элемент пирамиды располагается по середине своего массива,
        для следующих массивов будем декрементировать на 1 индекс влево */
        int startIndex = (pyramid[0].length) / 2;

        for (int i = 0; i < pyramid.length; i++) {

            int index = startIndex;

            for (int j = 0; j <= i; j++) {

                /*
                 * Записываем в массив вместо 0 значение из очереди и удаляем его из очереди.
                 * Если в массиве должно быть несколько значений из очереди, что справедливо для всех массивов
                 * кроме верхнего массива пирамиды, инкрементируем индекс на 2, чтобы остался разделительный 0,
                 * и записываем следующее значение.
                 */

                pyramid[i][index] = numbers.removeFirst();
                index += 2;
            }

            startIndex--; // декрементируем стартовый индекс на 1 влево
        }
        return pyramid;
    }

    /**
     * Возвращает высоту пирамиды, т.е. количество строк в двумерном массиве, если пирамиду можно построить на
     * основе переданного в параметр листа. Проверяет, существует ли такая арифметическая прогрессия,
     * далее АП, у которой начальный элемент АП ->  a1 = 1, разность АП -> d = 1, сумма всех элементов АП равна длине
     * листа. Если существует, то возвращает кол-во строк в АП, т.е. высоту пирамиды.
     * Используется формула x(x+1)/2 = sum, где sum - сумма всех членов АП, x - количество элементов.
     * Сумма равна длине листа, требуется выразить x и найти его.
     * x^2 + x - 2sum = 0;
     * D = b^2 - 4ac;
     * x1 = (-1+sqrt(D))/2;
     * x2 = (-1-sqrt(D))/2; // этот корень интересовать нас не будет, т.к. x2 < 0
     * Арифметическая прогрессия будет существовать, если найдётся такой x, что x > 0 и значение x будет недробным.
     * Следовательно, из такого листа можно построить пирамиду, а x будет высотой пирамиды.
     * Количество строк в пирамиде будет равно последнему элементу АП. В данной прогрессии последний
     * элемент равен кол-ву элементов.
     * Если мы знаем первый элемент АП и разность АП, то можем найти любой элемент по формуле:
     * an = a1 + (n-1)d, где аn - n-ный элемент АП, n - порядковый номер элемента в АП, d - разность АП.
     * В случае, если a1 = 1, d = 1, то an = 1 + n - 1 = n, т.е. последний элемент АП равен своему порядковому
     * номеру, а значит и количеству элементов АП.
     *
     * @param input list of integers
     * @return pyramid height if this list can be converted to a pyramid. Else returns -1.
     */

    public int getPyramidHeight(List<Integer> input) {
        int inputListSize = input.size();
        double result, discriminant;
        discriminant = (1 - 4 * 1 * (-2) * inputListSize);
        result = (-1 + Math.sqrt(discriminant)) / 2;
        if (result == Math.ceil(result)) {   // проверка, является ли полученный корень целым числом
            return (int) result;
        }
        return -1;
    }
}