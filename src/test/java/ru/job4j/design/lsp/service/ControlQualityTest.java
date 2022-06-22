package ru.job4j.design.lsp.service;

import org.junit.Test;
import ru.job4j.design.lsp.entity.Food;
import ru.job4j.design.lsp.entity.Meat;
import ru.job4j.design.lsp.entity.Pasta;
import ru.job4j.design.lsp.store.Shop;
import ru.job4j.design.lsp.store.Store;
import ru.job4j.design.lsp.store.Trash;
import ru.job4j.design.lsp.store.Warehouse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class ControlQualityTest {
    @Test
    public void whenFoodToWarehouse() {
        Store shop = new Shop();
        Store trash = new Trash();
        Store warehouse = new Warehouse();
        List<Store> storeList = Arrays.asList(shop, trash, warehouse);
        LocalDate createDate = LocalDate.now().minusDays(1);
        LocalDate expiryDate = createDate.plusDays(30);
        List<Food> foods = Arrays.asList(new Meat("Pork", createDate, expiryDate, 130.0));
        ControlQuality controlQuality = new ControlQuality(storeList);
        controlQuality.supply(foods);
        assertThat(warehouse.findBy(x -> true), is(foods));
    }

    @Test
    public void whenFoodToShop() {
        Store shop = new Shop();
        Store trash = new Trash();
        Store warehouse = new Warehouse();
        List<Store> storeList = Arrays.asList(shop, trash, warehouse);
        LocalDate createDate = LocalDate.now().minusDays(4);
        LocalDate expiryDate = createDate.plusDays(13);
        List<Food> foods = Arrays.asList(new Pasta("Spaghetti", createDate, expiryDate, 35.0));
        ControlQuality controlQuality = new ControlQuality(storeList);
        controlQuality.supply(foods);
        assertThat(shop.findBy(x -> true), is(foods));
    }

    @Test
    public void whenFoodToShopWithDiscount() {
        Store shop = new Shop();
        Store trash = new Trash();
        Store warehouse = new Warehouse();
        List<Store> storeList = Arrays.asList(shop, trash, warehouse);
        LocalDate createDate = LocalDate.now().minusDays(9);
        LocalDate expiryDate = createDate.plusDays(10);
        Food spaghetti = new Pasta("Spaghetti", createDate, expiryDate, 100.0);
        assertThat(spaghetti.getDiscount(), is(0.0));
        List<Food> foods = new ArrayList<>();
        foods.add(spaghetti);
        ControlQuality controlQuality = new ControlQuality(storeList);
        controlQuality.supply(foods);
        assertThat(shop.findBy(x -> true).get(0).getDiscount(), is(15.0));
    }

    @Test
    public void whenFoodToTrash() {
        Store shop = new Shop();
        Store trash = new Trash();
        Store warehouse = new Warehouse();
        List<Store> storeList = Arrays.asList(shop, trash, warehouse);
        LocalDate createDate = LocalDate.now().minusDays(10);
        LocalDate expiryDate = createDate.plusDays(8);
        List<Food> foods = Arrays.asList(new Pasta("Spaghetti", createDate, expiryDate, 15.0));
        ControlQuality controlQuality = new ControlQuality(storeList);
        controlQuality.supply(foods);
        assertThat(trash.findBy(x -> true), is(foods));
    }
}