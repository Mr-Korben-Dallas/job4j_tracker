package ru.job4j.design.lsp.service;

import ru.job4j.design.lsp.entity.Food;
import ru.job4j.design.lsp.store.Store;

import java.util.List;

public class ControlQuality {
    List<Store> storeList;

    public ControlQuality(List<Store> storeList) {
        this.storeList = storeList;
    }

    public void supply(List<Food> foodList) {
        for (Food food : foodList) {
            for (Store store : storeList) {
                if (store.add(food)) {
                    break;
                }
            }
        }
    }
}
