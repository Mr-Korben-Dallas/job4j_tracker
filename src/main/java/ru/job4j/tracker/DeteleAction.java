package ru.job4j.tracker;

public class DeteleAction implements UserAction {
    @Override
    public String name() {
        return "Delete Item";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println("=== Delete item ===");
        int id = input.askInt("Enter id: ");
        if (tracker.delete(id)) {
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("Item deletion error.");
        }
        return true;
    }
}