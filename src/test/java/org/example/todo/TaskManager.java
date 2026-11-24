package org.example.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new TaskManager();
    }

    @Test
    void addItemIncrementsCount() {
        manager.addItem("Купить хлеб");
        manager.addItem("Позвонить маме");
        assertEquals(2, manager.taskCount());
    }

    @Test
    void addItemAssignsSequentialIds() {
        Task first = manager.addItem("Первая");
        Task second = manager.addItem("Вторая");
        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    void addItemRejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> manager.addItem(" "));
    }

    @Test
    void completeItemMarksTaskDone() {
        Task task = manager.addItem("Тест");
        assertTrue(manager.completeItem(task.getId()));
        assertTrue(task.isCompleted());
    }

    @Test
    void completeItemReturnsFalseForUnknownId() {
        assertFalse(manager.completeItem(999));
    }

    @Test
    void removeItemDeletesTask() {
        Task task = manager.addItem("Удалить меня");
        assertTrue(manager.removeItem(task.getId()));
        assertEquals(0, manager.taskCount());
    }

    @Test
    void removeItemReturnsFalseForUnknownId() {
        assertFalse(manager.removeItem(42));
    }

    @Test
    void getTasksReturnsImmutableCopy() {
        manager.addItem("Задача");
        var tasks = manager.getTasks();
        assertThrows(UnsupportedOperationException.class, () -> tasks.add(new Task(99, "x")));
    }
}