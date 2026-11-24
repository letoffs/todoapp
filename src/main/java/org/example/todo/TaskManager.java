package org.example.todo;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task addItem(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым");
        }
        Task task = new Task(nextId++, title);
        tasks.add(task);
        return task;
    }

    public boolean removeItem(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    public boolean completeItem(int id) {
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .map(task -> {
                    task.markCompleted();
                    return true;
                })
                .orElse(false);
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }

    public int taskCount() {
        return tasks.size();
    }
}