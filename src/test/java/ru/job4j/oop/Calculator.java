package ru.job4j.oop;

public class Calculator {
    private static int x = 5;

    public static int minus(int a) {
        return x - a;
    }

    public static int sum(int y) {
        return x + y;
    }

    public int multiply(int a) {
        return x * a;
    }

    public int divide(int a) {
        return x / a;
    }

    public int sumAllOperation(int a) {
        return sum(a) + multiply(a) + minus(a) + divide(a);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        int multiplicationResult = calculator.multiply(5);
        int sumAllOperation = calculator.sumAllOperation(12);
        int sumOfNumbers = sum(2);
        int differenceOfNumbers = minus(4);
        System.out.println(multiplicationResult);
        System.out.println(sumAllOperation);
        System.out.println(sumOfNumbers);
        System.out.println(differenceOfNumbers);
    }
}
