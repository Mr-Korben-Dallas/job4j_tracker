package ru.job4j.stream;

import java.util.List;
import java.util.stream.Collectors;

public class StreamUsage {
    public static class Task {
        private final String name;
        private final long spent;

        public Task(String name, long spent) {
            this.name = name;
            this.spent = spent;
        }
    }

    public static void main(String[] args) {
        List<Task> tasks = List.of(
                new Task("Bug #1", 20),
                new Task("Task #2", 24),
                new Task("Bug #3", 55)
        );
        tasks.stream()
                .filter(task -> task.name.contains("Bug"))
                .filter(task -> task.spent > 30)
                .map(task -> task.name)
                .forEach(System.out::println);
    }
}