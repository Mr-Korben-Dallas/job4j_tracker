package ru.job4j.tracker;

import ru.job4j.tracker.model.Item;

import java.util.List;

public class ShowAllAction implements UserAction {
    private final Output output;

    public ShowAllAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Show all items";
    }

    @Override
    public boolean execute(Input input, Store store) {
        output.println("=== Show all items ===");
        List<Item> items = store.findAll();
        if (items.size() > 0) {
            for (Item item : items) {
                output.println(item);
            }
        } else {
            output.println("The MemTracker does not yet contain any items");
        }
        return true;
    }
}
