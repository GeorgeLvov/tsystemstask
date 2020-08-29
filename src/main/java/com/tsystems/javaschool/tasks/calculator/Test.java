package com.tsystems.javaschool.tasks.calculator;

public class Test {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        String stm = "12-56/2+(12-2*6)";
        System.out.println(calculator.evaluate(stm));
    }
}
