package org.example.todo;

import java.util.Scanner;

public class TodoApp {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Todo Application ===");
        printHelp();

        while (running) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "add" -> {
                    try {
                        Task task = manager.addItem(argument);
                        System.out.println("Добавлено: " + task);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                }
                case "list" -> {
                    if (manager.taskCount() == 0) {
                        System.out.println("Список задач пуст.");
                    } else {
                        manager.getTasks().forEach(System.out::println);
                    }
                }
                case "done" -> {
                    if (argument.isEmpty()) {
                        System.out.println("Укажите ID задачи: done <id>");
                        continue;
                    }
                    int id = Integer.parseInt(argument);
                    System.out.println(manager.completeItem(id)
                            ? "Задача " + id + " выполнена."
                            : "Задача " + id + " не найдена.");
                }
                case "remove" -> {
                    if (argument.isEmpty()) {
                        System.out.println("Укажите ID задачи: remove <id>");
                        continue;
                    }
                    int id = Integer.parseInt(argument);
                    System.out.println(manager.removeItem(id)
                            ? "Задача " + id + " удалена."
                            : "Задача " + id + " не найдена.");
                }
                case "help" -> printHelp();
                case "exit", "quit" -> running = false;
                default -> System.out.println("Неизвестная команда. Введите 'help'.");
            }
        }
        System.out.println("Выход.");
        scanner.close();
    }

    private static void printHelp() {
        System.out.println("""
                Команды:
                  add <текст>    - добавить задачу
                  list           - показать список
                  done <id>      - отметить выполненной
                  remove <id>    - удалить задачу
                  help           - помощь
                  exit           - выход
                """);
    }
}