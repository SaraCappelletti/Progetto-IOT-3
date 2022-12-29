package com.roomservice.scheduler;

import com.roomservice.task.Task;

import java.util.LinkedList;
import java.util.List;

public class Scheduler implements Runnable {
    final private List<Task> taskList;

    public Scheduler(final List<Task> taskList) {
        this.taskList = new LinkedList<>(taskList);
    }

    public void addTask(final Task task) {
        this.taskList.add(task);
    }
    public void removeTask(final Task task) {
        this.taskList.remove(task);
    }

    @Override
    public void run() {
        while (true) {
            this.taskList.forEach(Task::execute);
        }
    }
}
