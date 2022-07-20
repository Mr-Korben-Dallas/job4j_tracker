package ru.job4j.collection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class JobTest {
    @Test
    public void whenCompatorByNameAndPrority() {
        Comparator<Job> cmpNamePriority = new JobDescByName().thenComparing(new JobDescByPriority());
        int rsl = cmpNamePriority.compare(
                new Job("Impl task", 0),
                new Job("Fix bug", 1)
        );
        assertThat(rsl).isLessThan(0);
    }

    @Test
    public void whenCompatorByNameDesc() {
        Job task1 = new Job("Fix bug", 0);
        Job task2 = new Job("Impl task", 1);
        List<Job> list = Arrays.asList(task1, task2);
        List<Job> expected = List.of(task2, task1);
        list.sort(new JobDescByName());
        assertThat(expected).isEqualTo(list);
    }

    @Test
    public void whenCompatorByPriorityDesc() {
        Job task1 = new Job("Fix bug", 1);
        Job task2 = new Job("Impl task", 0);
        List<Job> list = Arrays.asList(task1, task2);
        List<Job> expected = List.of(task1, task2);
        list.sort(new JobDescByPriority());
        assertThat(expected).isEqualTo(list);
    }

    @Test
    public void whenCompatorByPriorityAsc() {
        Job task1 = new Job("Fix bug", 1);
        Job task2 = new Job("Impl task", 0);
        Job task3 = new Job("Close Ticket", 2);
        List<Job> list = Arrays.asList(task1, task2, task3);
        List<Job> expected = List.of(task2, task1, task3);
        list.sort(new JobAscByPriority());
        assertThat(expected).isEqualTo(list);
    }

    @Test
    public void whenCompatorByNameAsc() {
        Job task1 = new Job("Fix bug", 1);
        Job task2 = new Job("Impl task", 0);
        Job task3 = new Job("Close Ticket", 2);
        List<Job> list = Arrays.asList(task1, task2, task3);
        List<Job> expected = List.of(task3, task1, task2);
        list.sort(new JobAscByName());
        assertThat(expected).isEqualTo(list);
    }

    @Test
    public void whenCompatorByNameAndByPriorityAsc() {
        Job task1 = new Job("Fix bug", 1);
        Job task2 = new Job("Impl task", 0);
        Job task3 = new Job("Close Ticket", 2);
        List<Job> list = Arrays.asList(task1, task2, task3);
        List<Job> expected = List.of(task3, task1, task2);
        list.sort(new JobAscByName().thenComparing(new JobAscByPriority()));
        assertThat(expected).isEqualTo(list);
    }

    @Test
    public void whenCompatorByAscNameAndDescByPriority() {
        Job task1 = new Job("Fix bug", 1);
        Job task2 = new Job("Impl task", 0);
        Job task3 = new Job("Close Ticket", 2);
        List<Job> list = Arrays.asList(task1, task2, task3);
        List<Job> expected = List.of(task3, task1, task2);
        list.sort(new JobAscByName().thenComparing(new JobDescByPriority()));
        assertThat(expected).isEqualTo(list);
    }

    @Test public void whenCompatorByNameAndByPriorityDesc() {
        Job task1 = new Job("Fix bug", 1);
        Job task2 = new Job("Impl task", 0);
        Job task3 = new Job("Close Ticket", 2);
        Job task4 = new Job("Open Ticket", 3);
        List<Job> list = Arrays.asList(task1, task2, task3, task4);
        List<Job> expected = List.of(task4, task2, task1, task3);
        list.sort(new JobDescByName().thenComparing(new JobDescByPriority()));
        assertThat(expected).isEqualTo(list);
    }

    @Test
    public void whenCompatorByPriorityAndByNameDesc() {
        Job task1 = new Job("Fix bug", 1);
        Job task2 = new Job("Impl task", 0);
        Job task3 = new Job("Close Ticket", 2);
        Job task4 = new Job("Open Ticket", 3);
        Job task42 = new Job("Open Ticket", 3);
        List<Job> list = Arrays.asList(task1, task2, task3, task4, task42);
        List<Job> expected = List.of(task4, task42, task2, task1, task3);
        list.sort(new JobDescByName().thenComparing(new JobDescByPriority()));
        assertThat(expected).isEqualTo(list);
    }

    @Test
    public void whenCompatorByPriorityAndByNameDescWithDuplicates() {
        Job task1 = new Job("Fix bug", 1);
        Job task12 = new Job("Fix bug", 1);
        Job task13 = new Job("Fix bug", 1);
        Job task2 = new Job("Impl task", 0);
        Job task22 = new Job("Impl task", 0);
        Job task3 = new Job("Close Ticket", 2);
        Job task4 = new Job("Open Ticket", 3);
        Job task42 = new Job("Open Ticket", 3);
        Job task43 = new Job("Open Ticket", 3);
        List<Job> list = Arrays.asList(task1, task12, task13, task2, task22, task3, task4, task42, task43);
        List<Job> expected = List.of(task4, task42, task43, task2, task22, task1, task12, task13, task3);
        list.sort(new JobDescByName().thenComparing(new JobDescByPriority()));
        assertThat(expected).isEqualTo(list);
    }
}