package roomservice;

import roomservice.scheduler.Scheduler;
import roomservice.task.Task;
import roomservice.task.communication.http.HttpCommunicationTask;
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

        try {
//            final Task MqttCommunicationTask = new MqttCommunicationTask(args[0]);
//            final Task HttpunicationTask = new HttpCommunicationTask(args[0]);
            final Task serialCommunicationTask = new SerialCommunicationTask(args[0]);
//            final Task httpCommunicationTask = new HttpCommunicationTask();
            final Scheduler scheduler = new Scheduler(
                    Set.of(
//                        new MqttCommunicationTask(),
//                            httpCommunicationTask//,
                            serialCommunicationTask
                    )
            );

            scheduler.schedule();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
