package task;

import task.exception.IncorrectArgumentException;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task{

    public WeeklyTask(String title, Type type, LocalDateTime dateTime, String description) throws IncorrectArgumentException {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate date){
        return date.isAfter(getDateTime().toLocalDate())
                || date.isEqual(getDateTime().toLocalDate())
                && date.getDayOfWeek() == getDateTime().getDayOfWeek();
    }
}
