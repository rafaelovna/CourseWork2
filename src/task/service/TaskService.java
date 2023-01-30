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
            if (entry.getValue() != null && (entry.getValue().getDateTime().toLocalDate().equals(date))) {
                tasks.add(entry.getValue());
            }
        }
        return tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskService that = (TaskService) o;
        return Objects.equals(taskMap, that.taskMap) && Objects.equals(removedTask, that.removedTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskMap, removedTask);
    }

    @Override
    public String toString() {
        return "TaskService{" +
                "taskMap=" + taskMap +
                ", removedTask=" + removedTask +
                '}';
    }
}
