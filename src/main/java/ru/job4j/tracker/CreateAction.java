package ru.job4j.tracker;

public class CreateAction implements UserAction {
    private final Output output;

    public CreateAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Add new Item";
    }

    @Override
    public boolean execute(Input input, Store store) {
        output.println("=== Add a new Item ===");
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        store.add(item);
        output.println("You added item: " + item);
        return true;
    }
}
