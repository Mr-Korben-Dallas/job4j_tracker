package ru.job4j.design.lsp.store;

import ru.job4j.design.lsp.entity.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Warehouse implements Store {
    private List<Food> foodList = new ArrayList<>();

    @Override
    public List<Food> findBy(Predicate<Food> filter) {
        return foodList.stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public boolean add(Food food) {
        double percent = expirationPercent(food.getCreateDate(), food.getExpiryDate());
        if (percent < 25) {
            foodList.add(food);
            return true;
        }
        return false;
    }
}
