package ru.job4j.tracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ItemDescByNameTest {
    @Test
    public void whenTestSortItemsDescByName() {
        List<Item> items = new ArrayList<>(List.of(
                new Item("2"),
                new Item("1"),
                new Item("4"),
                new Item("3"),
                new Item("9"),
                new Item("8")
        ));
        List<Item> expected = new ArrayList<>(List.of(
                new Item("9"),
                new Item("8"),
                new Item("4"),
                new Item("3"),
                new Item("2"),
                new Item("1")
        ));
        Collections.sort(items, new ItemDescByName());
        assertEquals(expected, items);
    }
}