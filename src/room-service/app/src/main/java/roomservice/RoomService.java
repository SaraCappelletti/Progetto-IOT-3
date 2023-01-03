package roomservice;

import roomservice.scheduler.Scheduler;
import roomservice.smartroom.SmartRoom;
import roomservice.smartroom.SmartRoomImpl;
import roomservice.task.Task;
import roomservice.task.communication.http.HttpCommunicationTask;
import roomservice.task.communication.mqtt.MqttCommunicationTask;
import roomservice.task.communication.serial.SerialCommunicationTask;

import java.util.Set;
import org.apache.commons.lang3.SystemUtils;

public class RoomService {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: " + (System.getenv("SHELL") != null ? "./gradlew" : ".\\gradlew.bat") + " --args=\"<Port name for arduino serial communication>\"");
            return;
        }
        System.out.println("--------------------\n" +
                            "Room-Service started\n" +
                            "--------------------");

        SmartRoom room = new SmartRoomImpl();

        try {
//            final Task httpCommunicationTask = new HttpCommunicationTask(room);
//            final Task mqttCommunicationTask = new MqttCommunicationTask(room);
            final Task serialCommunicationTask = new SerialCommunicationTask(args[0], room);

            final Scheduler scheduler = new Scheduler(
                    Set.of(
//                            httpCommunicationTask//,
//                            mqttCommunicationTask(),
                            serialCommunicationTask
                    )
            );

            scheduler.schedule();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
