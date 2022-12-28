package com.roomservice;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class RoomService {
    public static void main(String[] args) {
        System.out.println(
            "--------------------\n" +
            "Room-Service started\n" +
            "--------------------");

        final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        final Scheduler scheduler = schedulerFactory.getScheduler();

    }
}
