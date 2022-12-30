package roomservice.scheduler;

import roomservice.task.Task;

import java.util.LinkedHashSet;
import java.util.Set;

public class Scheduler {

    private Set<Task> taskSet;

    public Scheduler(final Set<Task> taskSet) {
        this.taskSet = new LinkedHashSet<>(taskSet);
    }

    public void schedule() {
        while (true) {
            this.taskSet.forEach(Task::execute);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
