package ru.job4j.tracker;

import ru.job4j.tracker.model.Item;

public class FindByIdAction implements UserAction {
    private final Output output;

    public FindByIdAction(Output output) {
        this.output = output;
    }
    
    @Override
    public String name() {
        return "Find item by id";
    }

    @Override
    public boolean execute(Input input, Store store) {
        output.println("=== Find item by id ===");
        int id = input.askInt("Enter id: ");
        Item item = store.findById(id);
        if (item != null) {
            output.println(item);
        } else {
            output.println("Item with id: " + id + " not found.");
        }
        return true;
    }
}
