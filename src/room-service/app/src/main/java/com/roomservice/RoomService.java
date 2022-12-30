package com.roomservice;

import com.roomservice.scheduler.Scheduler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class RoomService {
    public static void main(String[] args) {
        System.out.println("--------------------\n" +
                            "Room-Service started\n" +
                            "--------------------");

        final Scheduler scheduler = new Scheduler();

        scheduler.start();

//        scheduler.interrupt();
    }
}
