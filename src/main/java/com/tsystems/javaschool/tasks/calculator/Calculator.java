package com.tsystems.javaschool.tasks.calculator;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;


import static com.tsystems.javaschool.tasks.calculator.RPNConverter.getPriority;


public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {

        /* проверка строки на валидность */
        if (!ExpressionValidator.isExpressionValid(statement)) {
            return null;
        }

        /* Подготваливаем выражение для возможности вычисления унарного минуса */
        String preparedExpression = preparingExpression(statement);

        /* Конвертируем выражение в обратную польскую нотацию(Reverse Polish notation) */
        RPNConverter converter = new RPNConverter();
        String rpn = converter.convert(preparedExpression);

        /* Вычисление результата до форматирования */
        String result = calculate(rpn);

        return result != null ? roundResult(result) : null;
    }


    /**
     * Данный метод позволяет работать с отрицательными числами, т.е. с унарным минусом.
     * Метод добавляет в строку 0, если минус стоит в начале строки, либо перед открывающей скобкой.
     * Примеры преобразований:
     * 4-(-2) -> 4-(0-2)
     * -(-2)  -> 0-(0-2)
     *
     * @param expression mathematical expression
     * @return prepared mathematical expression for working with unary minuses
     */
    private String preparingExpression(String expression) {
        String preparedExpression = "";
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '-') {
                if (i == 0) {
                    preparedExpression += '0';
                } else if (expression.charAt(i - 1) == '(') {
                    preparedExpression += '0';
                }
            }
            preparedExpression += ch;
        }
        return preparedExpression;
    }

    /**
     * Вычисляет выражение на основе обратной польской нотации.
     *
     * @param rpn reverse polish notation
     * @return string value containing unformatted result of calculation or null if statement
     * contains division by zero
     */
    private String calculate(String rpn) {
        String operand = "";
        Queue<Double> numbersQueue = Collections.asLifoQueue(new ArrayDeque<>());

        for (int i = 0; i < rpn.length(); i++) {

            if (rpn.charAt(i) == ' ') {
                continue;
            }

            /*
             * Если число в выражении содержит более одной цифры или оно дробное, то
             * вычитываем его полностью, пока не встретится пробел или оператор, и добавляем в очередь
             */
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) break;
                }
                numbersQueue.offer(Double.parseDouble(operand));
                operand = "";
            }

            /*
             * Если в строке встретился символ арифметической операции, тогда берем из очереди
             * 2 элемента и проводим между ними эту операцию
             */
            if (getPriority(rpn.charAt(i)) > 1) {

                double a = numbersQueue.remove();
                double b = numbersQueue.remove();

                // В случае деления на 0, возвращаем null
                if (a == 0 && rpn.charAt(i) == '/') {
                    return null;
                }

                switch (rpn.charAt(i)) {
                    case '+':
                        numbersQueue.offer(b + a);
                        break;
                    case '-':
                        numbersQueue.offer(b - a);
                        break;
                    case '*':
                        numbersQueue.offer(b * a);
                        break;
                    case '/':
                        numbersQueue.offer(b / a);
                        break;
                }
            }
        }

        return String.valueOf(numbersQueue.remove());
    }

    /**
     * Метод форматирует полученный результат согласно условию.
     * Пример: 102.12356 -> 102.1236
     *
     * @param result string value containing unformatted result of evaluation
     * @return string value containing rounded result of evaluation
     */
    private String roundResult(String result) {
        double res = Double.parseDouble(result);
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        return decimalFormat.format(res).replace(",", ".");
    }

}