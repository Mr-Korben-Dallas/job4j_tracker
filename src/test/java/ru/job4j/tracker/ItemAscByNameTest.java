package ru.job4j.tracker;

import ru.job4j.tracker.model.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class ItemAscByNameTest {
    @Test
    public void whenTestSortItemsAscByName() {
        List<Item> items = new ArrayList<>(List.of(
                new Item("6"),
                new Item("5"),
                new Item("4"),
                new Item("3"),
                new Item("2"),
                new Item("1")
        ));
        List<Item> expected = new ArrayList<>(List.of(
                new Item("1"),
                new Item("2"),
                new Item("3"),
                new Item("4"),
                new Item("5"),
                new Item("6")
        ));
        Collections.sort(items, new ItemAscByName());
        assertThat(expected).isEqualTo(items);
    }
}