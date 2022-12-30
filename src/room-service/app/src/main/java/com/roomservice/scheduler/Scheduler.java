package com.roomservice.scheduler;

import com.roomservice.task.Task;

import java.util.LinkedHashSet;
import java.util.Set;

public class Scheduler extends Thread {
    private Set<Task> taskSet;
    private Set<Integer> list;

    public Scheduler() {
        this(Set.of());
    }

    public Scheduler(final Set<Task> taskSet) {
        this.taskSet = new LinkedHashSet<>(taskSet);
    }

    public void addTask(final Task task) {
        this.taskSet.add(task);
    }
    public void removeTask(final Task task) {
        this.taskSet.remove(task);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                this.taskSet.forEach(Task::execute);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {}
    }
}
