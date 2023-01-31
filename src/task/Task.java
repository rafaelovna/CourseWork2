package task;

import task.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task {
    private static int idGenerator = 1;
    private String title;
    private Type type;
    private int id;
    private LocalDateTime dateTime;
    private String description;


    public Task(String title, Type type, LocalDateTime dateTime, String description) throws IncorrectArgumentException {
        if (type == null) {
            throw new IncorrectArgumentException("<ТИП ЗАДАЧИ>");
        } else {
            this.type = type;
        }
        if (dateTime == null) {
            throw new IncorrectArgumentException("<ДАТА И ВРЕМЯ>");
        } else {
            this.dateTime = dateTime;
        }
        setTitle(title);
        setDescription(description);
        this.id = idGenerator++;
    }

    public abstract boolean appearsIn(LocalDate date) throws IncorrectArgumentException;

    public String getTitle() {
        return title;
    }

    public Type getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }


    public void setTitle(String title) throws IncorrectArgumentException {
        if (title == null || title.isEmpty()) {
            throw new IncorrectArgumentException("<ЗАГОЛОВОК>");
        } else {
            this.title = title;
        }
    }

    public void setDescription(String description) throws IncorrectArgumentException {
        if (description == null || description.isEmpty()) {
            throw new IncorrectArgumentException("<ОПИСАНИЕ ЗАДАЧИ>");
        } else {
            this.description = description;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(title, task.title) && type == task.type && Objects.equals(dateTime, task.dateTime) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, type, id, dateTime, description);
    }

    @Override
    public String toString() {
        return "Задача: " +
                "id--> " + id +
                ". Заголовок: <<" + title +
                ">>. " + type +
                ". Дата и время: " + dateTime +
                ". Описание: " + description;
    }
}
