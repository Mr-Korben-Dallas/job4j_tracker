package ru.job4j.tracker;

public class DeteleAction implements UserAction {
    private final Output output;

    public DeteleAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Delete Item";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        output.println("=== Delete item ===");
        int id = input.askInt("Enter id: ");
        if (tracker.delete(id)) {
            output.println("Item deleted successfully.");
        } else {
            output.println("Item deletion error.");
        }
        return true;
    }
}
