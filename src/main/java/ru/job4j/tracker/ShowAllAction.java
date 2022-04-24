package ru.job4j.tracker;

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
    public boolean execute(Input input, Tracker tracker) {
        output.println("=== Show all items ===");
        List<Item> items = tracker.findAll();
        if (items.size() > 0) {
            for (Item item : items) {
                output.println(item);
            }
        } else {
            output.println("The Tracker does not yet contain any items");
        }
        return true;
    }
}
