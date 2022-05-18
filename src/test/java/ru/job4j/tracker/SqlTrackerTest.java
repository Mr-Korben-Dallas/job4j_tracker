package ru.job4j.tracker;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class SqlTrackerTest {
    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenAddNewItemThenTrackerHasThisItem() {
        Store tracker = new SqlTracker(connection);
        Item item = new Item("item");
        assertThat(tracker.add(item), is(item));
    }

    @Test
    public void whenAllItemsFounded() {
        Store tracker = new SqlTracker(connection);
        List<Item> items = List.of(
                new Item("First"),
                new Item("Second"),
                new Item("Third")
        );
        items.forEach(tracker::add);
        assertThat(tracker.findAll(), is(items));
    }

    @Test
    public void whenItemFoundByName() {
        Store tracker = new SqlTracker(connection);
        List<Item> items = List.of(
                new Item("First"),
                new Item("Second"),
                new Item("Third")
        );
        items.forEach(tracker::add);
        assertThat(tracker.findByName("Second").size(), is(1));
    }

    @Test
    public void whenDeleteIdThenTrackerFindByIdGetNull() {
        Store tracker = new SqlTracker(connection);
        Item item = new Item("First");
        tracker.add(item);
        int id = item.getId();
        tracker.delete(id);
        assertThat(tracker.findById(id), is(nullValue()));
    }

    @Test
    public void whenItemReplaced() {
        Store tracker = new SqlTracker(connection);
        Item item = new Item("Some item");
        tracker.add(item);
        int id = item.getId();
        Item itemWithNewName = new Item("Set new name to item");
        tracker.replace(id, itemWithNewName);
        assertThat(tracker.findById(id).getName(), is("Set new name to item"));
    }
}
