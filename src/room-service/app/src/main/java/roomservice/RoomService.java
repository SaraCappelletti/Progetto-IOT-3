package roomservice;

import roomservice.scheduler.Scheduler;
import roomservice.task.communication.HttpCommunicationTask;
import roomservice.task.communication.MqttCommunicationTask;
import roomservice.task.communication.SerialCommunicationTask;

import java.util.Set;

public class RoomService {
    public static void main(String[] args) {
        System.out.println("--------------------\n" +
                            "Room-Service started\n" +
                            "--------------------");

        final Scheduler scheduler = new Scheduler(
                Set.of(
                        new MqttCommunicationTask(),
                        new HttpCommunicationTask(),
                        new SerialCommunicationTask()

                )
        );

        scheduler.schedule();
    }
}
