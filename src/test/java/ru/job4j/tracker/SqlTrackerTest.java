package ru.job4j.tracker;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class SqlTrackerTest {
    private static Connection connection;

    @BeforeAll
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

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @AfterEach
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
        assertThat(tracker.findById(item.getId())).isEqualTo(item);
    }

    @Test
    public void whenAddNewItemThenTrackerHasThisItem() {
        Store tracker = new SqlTracker(connection);
        Item item = new Item("item");
        assertThat(tracker.add(item)).isEqualTo(item);
    }

    @Test
    public void whenAllItemsFounded() {
        Store tracker = new SqlTracker(connection);
        Item first = tracker.add(new Item("First"));
        Item second = tracker.add(new Item("Second"));
        Item third = tracker.add(new Item("third"));
        assertThat(tracker.findAll()).isEqualTo(List.of(first, second, third));
    }

    @Test
    public void whenItemFoundByName() {
        Store tracker = new SqlTracker(connection);
        Item first = tracker.add(new Item("First"));
        Item second = tracker.add(new Item("Second"));
        Item third = tracker.add(new Item("third"));
        assertThat(tracker.findByName("Second")).isEqualTo(List.of(second));
    }

    @Test
    public void whenDeleteIdThenTrackerFindByIdGetNull() {
        Store tracker = new SqlTracker(connection);
        Item item = new Item("First");
        tracker.add(item);
        int id = item.getId();
        tracker.delete(id);
        assertThat(tracker.findById(id)).isNull();
    }

    @Test
    public void whenItemReplaced() {
        Store tracker = new SqlTracker(connection);
        Item item = new Item("Some item");
        tracker.add(item);
        int id = item.getId();
        Item itemWithNewName = new Item("Set new name to item");
        tracker.replace(id, itemWithNewName);
        assertThat(tracker.findById(id).getName()).isEqualTo("Set new name to item");
    }
}
