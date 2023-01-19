package roomservice;

import roomservice.scheduler.Scheduler;
import roomservice.task.smartroom.SmartRoom;
import roomservice.task.smartroom.SmartRoomImpl;
import roomservice.task.Task;
import roomservice.task.communication.serial.SerialCommunicationTask;

import java.util.Set;

public class RoomService {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: " + (System.getenv("SHELL") != null ? "./gradlew" : ".\\gradlew.bat") + " --args=\"<Port name for arduino serial communication>\"");
            return;
        }
        System.out.println("--------------------\n" +
                            "Room-Service started\n" +
                            "--------------------");


        try {
            final Task room = new SmartRoomImpl();
//            final Task httpCommunicationTask = new HttpCommunicationTask((SmartRoom) room, 0);
//            final Task mqttCommunicationTask = new MqttCommunicationTask((SmartRoom) room, 1);
            final Task serialCommunicationTask = new SerialCommunicationTask(args[0], (SmartRoom) room, 2);

            final Scheduler scheduler = new Scheduler(
                    Set.of(
//                            mqttCommunicationTask,
//                            httpCommunicationTask//,
                            serialCommunicationTask,
                            room
                    )
            );

            scheduler.schedule();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
