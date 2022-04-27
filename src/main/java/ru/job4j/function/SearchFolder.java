package ru.job4j.function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class SearchFolder {
    public static List<Folder> filter(List<Folder> list, Predicate<Folder> pred) {
        List<Folder> result = new ArrayList<>();
        for (Folder listItem : list) {
            if (pred.test(listItem)) {
                result.add(listItem);
            }
        }
        return result;
    }
}