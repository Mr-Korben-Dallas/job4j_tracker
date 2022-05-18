package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StartUITest {
    @Test
    public void whenCreateItem() {
        Input in = new StubInput(
                new String[] {"0", "Item name", "1"}
        );
        Store memTracker = new MemTracker();
        Output output = new ConsoleOutput();
        List<UserAction> actions = new ArrayList<>(List.of(
                new CreateAction(output),
                new ExitAction()
        ));
        new StartUI(output).init(in, memTracker, actions);
        assertThat(memTracker.findAll().get(0).getName(), is("Item name"));
    }

    @Test
    public void whenEditItem() {
        Store memTracker = new MemTracker();
        Item item = memTracker.add(new Item("Replaced item"));
        String replacedName = "New item name";
        Output output = new ConsoleOutput();
        Input in = new StubInput(
                new String[] {"0", String.valueOf(item.getId()), replacedName, "1"}
        );
        List<UserAction> actions = new ArrayList<>(List.of(
                new EditAction(output),
                new ExitAction()
        ));
        new StartUI(output).init(in, memTracker, actions);
        assertThat(memTracker.findById(item.getId()).getName(), is(replacedName));
    }

    @Test
    public void whenDeleteItem() {
        Store memTracker = new MemTracker();
        Item item = memTracker.add(new Item("Deleted item"));
        Output output = new ConsoleOutput();
        Input in = new StubInput(
                new String[] {"0", String.valueOf(item.getId()), "1"}
        );
        List<UserAction> actions = new ArrayList<>(List.of(
                new DeteleAction(output),
                new ExitAction()
        ));
        new StartUI(output).init(in, memTracker, actions);
        assertNull(memTracker.findById(item.getId()));
    }

    @Test
    public void whenEditItemTestOutputIsSuccessfully() {
        Output out = new StubOutput();
        Store memTracker = new MemTracker();
        Item one = memTracker.add(new Item("test1"));
        String replaceName = "New Test Name";
        Input in = new StubInput(
                new String[] {"0", String.valueOf(one.getId()), replaceName, "1"}
        );
        List<UserAction> actions = new ArrayList<>(List.of(
                new EditAction(out),
                new ExitAction()
        ));
        new StartUI(out).init(in, memTracker, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu." + ln
                        + "0. Edit item" + ln
                        + "1. Exit" + ln
                        + "=== Edit item ===" + ln
                        + "The item changed successfully." + ln
                        + "Menu." + ln
                        + "0. Edit item" + ln
                        + "1. Exit" + ln
        ));
    }

    @Test
    public void whenFindAllTestOutputIsSuccessfully() {
        Output out = new StubOutput();
        Store memTracker = new MemTracker();
        Item one = memTracker.add(new Item("test1"));
        Item two = memTracker.add(new Item("test2"));
        Input in = new StubInput(new String[] {"0", "1"});
        List<UserAction> actions = new ArrayList<>(List.of(
                new ShowAllAction(out),
                new ExitAction()
        ));
        new StartUI(out).init(in, memTracker, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu." + ln
                        + "0. Show all items" + ln
                        + "1. Exit" + ln
                        + "=== Show all items ===" + ln
                        + one + ln
                        + two + ln
                        + "Menu." + ln
                        + "0. Show all items" + ln
                        + "1. Exit" + ln
        ));
    }

    @Test
    public void whenFindByNameTestOutputIsSuccessfully() {
        Output out = new StubOutput();
        Store memTracker = new MemTracker();
        Item one = memTracker.add(new Item("test1"));
        Input in = new StubInput(
                new String[] {"0", one.getName(), "1"}
        );
        List<UserAction> actions = new ArrayList<>(List.of(
                new FindByNameAction(out),
                new ExitAction()
        ));
        new StartUI(out).init(in, memTracker, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu." + ln
                        + "0. Find items by name" + ln
                        + "1. Exit" + ln
                        + "=== Find items by name ===" + ln
                        + one + ln
                        + "Menu." + ln
                        + "0. Find items by name" + ln
                        + "1. Exit" + ln
        ));
    }

    @Test
    public void whenFindByIdTestOutputIsSuccessfully() {
        Output out = new StubOutput();
        Store memTracker = new MemTracker();
        Item one = memTracker.add(new Item("test1"));
        Item two = memTracker.add(new Item("test2"));
        Input in = new StubInput(
                new String[] {"0", String.valueOf(two.getId()), "1"}
        );
        List<UserAction> actions = new ArrayList<>(List.of(
                new FindByIdAction(out),
                new ExitAction()
        ));
        new StartUI(out).init(in, memTracker, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu." + ln
                        + "0. Find item by id" + ln
                        + "1. Exit" + ln
                        + "=== Find item by id ===" + ln
                        + two + ln
                        + "Menu." + ln
                        + "0. Find item by id" + ln
                        + "1. Exit" + ln
        ));
    }

    @Test
    public void whenInvalidExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"9", "0"}
        );
        Store memTracker = new MemTracker();
        List<UserAction> actions = new ArrayList<>(List.of(
                new ExitAction()
        ));
        new StartUI(out).init(in, memTracker, actions);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is(
                "Menu." + ln
                        + "0. Exit" + ln
                        + "Wrong input, you can select: 0 .. 0" + ln
                        + "Menu." + ln
                        + "0. Exit" + ln
                )
        );
    }
}