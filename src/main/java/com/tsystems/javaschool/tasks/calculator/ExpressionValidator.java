package com.tsystems.javaschool.tasks.calculator;

/*
 * По условию строка может содержать:
 * 1. Цифры(0-9)
 * 2. Точки для дробных чисел
 * 3. Круглые скобки
 * 4. Символы арифметических операций ('+','-','*','/')
 *
 * Строка будет невалидной, если:
 * 1. Строка пустая или равна null
 * 2. Строка содержит склеенные арифметические операторы (++, --, **, //)
 * 3. Строка содержит какие-либо символы, кроме цифр(0-9), точек(.), круглых скобок и операторов (+,-, *,/)
 * 4. Строка содержит неодинаковое количество открывающих и закрывающих скобок
 * 5. Строка содержит пустые скобки () или скобки неправильно открыты )(
 * */

public class ExpressionValidator {

    public static boolean isExpressionValid(String inputExpression) {

        if (inputExpression == null || inputExpression.isEmpty()) {
            return false;
        }

        if ((inputExpression.contains("..")) || (inputExpression.contains("++")) || (inputExpression.contains("--")) ||
                (inputExpression.contains("**")) || (inputExpression.contains("//"))) {
            return false;
        }


        int leftParenthesisCount = 0;
        int rightParenthesisCount = 0;

        for (int i = 0; i < inputExpression.length(); i++) {
            char ch = inputExpression.charAt(i);
            if (!Character.isDigit(ch) && ch != '+' && ch != '-' && ch != '/' && ch != '*' && ch != '.'
                    && ch != '(' && ch != ')') {
                return false;
            }
            if (ch == '(') leftParenthesisCount++;
            if (ch == ')') rightParenthesisCount++;
        }
        if (leftParenthesisCount != rightParenthesisCount) {
            return false;
        }


        /* Если строка содержит пустые скобки или неправильно открытые, то false */

        for (int i = 0; i < inputExpression.length() - 1; i++) {
            if (inputExpression.charAt(i) == ')' && inputExpression.charAt(i + 1) == '(') {
                return false;
            }
            if (inputExpression.charAt(i) == '(' && inputExpression.charAt(i + 1) == ')') {
                return false;
            }
        }

        return true;
    }
}
