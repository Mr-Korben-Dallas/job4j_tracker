package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

public class StartUI {
    private final Output output;

    public StartUI(Output output) {
        this.output = output;
    }

    public void init(Input input, Tracker tracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ");
            if (select < 0 || select >= actions.size()) {
                output.println("Wrong input, you can select: 0 .. " + (actions.size() - 1));
                continue;
            }
            run = actions.get(select).execute(input, tracker);
        }
    }

    private void showMenu(List<UserAction> actions) {
        output.println("Menu.");
        for (int i = 0; i < actions.size(); i++) {
            output.println(i + ". " + actions.get(i).name());
        }
    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());
        Tracker tracker = new Tracker();
        List<UserAction> actions = new ArrayList<>(List.of(
                new CreateAction(output),
                new EditAction(output),
                new DeteleAction(output),
                new FindByIdAction(output),
                new FindByNameAction(output),
                new ShowAllAction(output),
                new ExitAction()
        ));
        new StartUI(output).init(input, tracker, actions);
    }
}