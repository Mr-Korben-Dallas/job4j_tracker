package ru.job4j.tracker;

public class EditAction implements UserAction {
    private final Output output;

    public EditAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Edit item";
    }

    @Override
    public boolean execute(Input input, Store store) {
        output.println("=== Edit item ===");
        int id = input.askInt("Enter id: ");
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        if (store.replace(id, item)) {
            output.println("The item changed successfully.");
        } else {
            output.println("Item replacement error.");
        }
        return true;
    }
}
