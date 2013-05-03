package mapping;

import models.Task;
import org.mongolink.domain.mapper.AggregateMap;

public class TaskMapping extends AggregateMap<Task> {
    public TaskMapping() {
        super(Task.class);
    }

    @Override
    public void map() {
        id(element().id).natural();
        property(element().label);
    }
}
