package task.service;

import task.Task;
import task.exception.TaskNotFoundException;

import java.time.LocalDate;
import java.util.*;

public class TaskService {
    public final Map<Integer, Task> taskMap = new TreeMap<>();
    public final Collection<Task> removedTask = new ArrayList<>();

    public void add(Task task) {
        this.taskMap.put(task.getId(), task);
    }

    public void remove(Integer taskId) throws TaskNotFoundException {
        if (this.taskMap.containsKey(taskId)) {
            taskMap.remove(taskId);
        } else {
            throw new TaskNotFoundException(taskId);
        }
    }

    public Collection<Task> getAllByDate(LocalDate date) {
        List<Task> tasks = new LinkedList<>();
        for (Map.Entry<Integer, Task> entry : taskMap.entrySet()) {
            if (entry.getValue().appearsIn(date)) {
                tasks.add(entry.getValue());
            }
        }
        return tasks;
    }
}
