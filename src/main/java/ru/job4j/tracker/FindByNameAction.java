package ru.job4j.tracker;

import java.util.List;

public class FindByNameAction implements UserAction {
    private final Output output;

    public FindByNameAction(Output output) {
        this.output = output;
    }
    
    @Override
    public String name() {
        return "Find items by name";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        output.println("=== Find items by name ===");
        String name = input.askStr("Enter name: ");
        List<Item> items = tracker.findByName(name);
        if (items.size() > 0) {
            for (Item item : items) {
                output.println(item);
            }
        } else {
            output.println("Items with name: " + name + " not found.");
        }
        return true;
    }
}
