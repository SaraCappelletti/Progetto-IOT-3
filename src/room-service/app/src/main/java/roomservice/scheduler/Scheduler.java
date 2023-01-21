package roomservice.scheduler;

import java.util.LinkedHashSet;
import java.util.Set;

import roomservice.task.Task;

public class Scheduler {

    final private Set<Task> taskSet;

    public Scheduler(final Set<Task> taskSet) {
        this.taskSet = new LinkedHashSet<>(taskSet);
    }

    public void schedule() {
        while (true) {
            this.taskSet.forEach(Task::execute);
            try {
                Thread.sleep(10);
//                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
        }
    }

}
