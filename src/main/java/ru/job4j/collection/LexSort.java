package ru.job4j.collection;

import java.util.Comparator;

public class LexSort implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        String[] leftString = left.split(". ");
        String[] rightString = right.split(". ");
        return Integer.compare(Integer.parseInt(leftString[0]), Integer.parseInt(rightString[0]));
    }
}