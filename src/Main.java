import task.*;
import task.exception.IncorrectArgumentException;
import task.exception.TaskNotFoundException;
import task.service.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static final TaskService taskService = new TaskService();
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.println("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            removeTask(scanner);
                        case 3:
                            printTaskByDate(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }


    private static void inputTask(Scanner scanner) {
        scanner.useDelimiter("\n");
        String title = inputTaskTitle(scanner);
        String description = inputTaskDescription(scanner);
        Type type = inputTaskType(scanner);
        LocalDateTime taskTime = inputTaskTime(scanner);
        int appearsIn = inputAppearsIn(scanner);

        createTask(title, description, type, taskTime, appearsIn);
    }

    private static void removeTask(Scanner scanner) {
        System.out.println("Введите ID задачи для удаления");
        int id = scanner.nextInt();
        try {
            taskService.remove(id);
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printTaskByDate(Scanner scanner) {
        System.out.println("Введите дату в формате dd.MM.yyyy");

        if (scanner.hasNext(DATE_PATTERN)) {
            String dateTime = scanner.next(DATE_PATTERN);

            LocalDate inputDate = LocalDate.parse(dateTime, DATE_FORMATTER);
            Collection<Task> tasks = taskService.getAllByDate(inputDate);
            for (Task task : tasks) {
                System.out.println(task);
            }
        } else {
            System.out.println("Введите дату в формате dd.MM.yyyy");
            scanner.close();
        }
    }

    private static String inputTaskTitle(Scanner scanner) {
        System.out.println("Введите название задачи: ");
        String title = scanner.next();
        if (title.isBlank()) {
            System.out.println("Необходимо ввести название задачи!");
            scanner.close();
        }
        return title;
    }

    private static String inputTaskDescription(Scanner scanner) {
        System.out.println("Введите описание задачи: ");
        String description = scanner.next();
        if (description.isBlank()) {
            System.out.println("Необходимо ввести описание задачи!");
            scanner.close();
        }
        return description;
    }

    private static Type inputTaskType(Scanner scanner) {
        System.out.println("Введите тип задачи(1 - личная, 2 - рабочая): ");
        Type type = null;
        int typeChoice = scanner.nextInt();
        switch (typeChoice) {
            case 1:
                type = Type.PERSONAL;
                break;
            case 2:
                type = Type.WORK;
                break;
            default:
                System.out.println("Тип задачи введен некорректно!");
                scanner.close();
        }
        return type;
    }

    private static LocalDateTime inputTaskTime(Scanner scanner) {
        System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm");

        if (scanner.hasNext(DATE_TIME_PATTERN)) {
            String dateTime = scanner.next(DATE_TIME_PATTERN);
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } else {
            System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm");
            scanner.close();
            return null;
        }
    }

    private static int inputAppearsIn(Scanner scanner) {
        System.out.println("Введите повторяемость задачи (1 - однократно, 2 - каждый день, " +
                "3 - каждую неделю, 4 - каждый месяц, 5 - каждый год");
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            System.out.println("Введите числом повторяемость задачи");
            scanner.close();
        }
        return -1;
    }

    private static void createTask(String title, String description, Type type, LocalDateTime taskTime, int appearsIn) {
        Task task = null;
        try {
            switch (appearsIn) {
                case 1:
                    task = new OneTimeTask(title, type, taskTime, description);
                    break;
                case 2:
                    task = new DailyTask(title, type, taskTime, description);
                    break;
                case 3:
                    task = new WeeklyTask(title, type, taskTime, description);
                    break;
                case 4:
                    task = new MonthlyTask(title, type, taskTime, description);
                    break;
                case 5:
                    task = new YearlyTask(title, type, taskTime, description);
                    break;
                default:
                    System.out.println("Повторяемость задачи введена некорректно");
            }
        } catch (IncorrectArgumentException e) {
            System.out.println(e.getMessage());
        }
        if (task != null) {
            taskService.add(task);
            System.out.println("Задача добавлена");
        } else {
            System.out.println("Введены некорректные данные по задаче");
        }
    }

    private static void printMenu() {
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день\n0. Выход");
    }
}