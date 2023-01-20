package roomservice.task.communication.serial;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import roomservice.task.smartroom.SmartRoom;
import roomservice.task.Task;

public class SerialCommunicationTask implements Task {

    final CommChannel channel;
    final SmartRoom room;
    final int priority;

    public SerialCommunicationTask(final String port, final SmartRoom room, final int priorityLevel) throws Exception {
        try {
            this.channel = new SerialCommChannel(port, 9600);
        } catch (Exception e) {
            throw new  Exception("Unable to create Serial-Communication Task");
        }
        this.room = room;
        this.priority = priorityLevel;
    }

    @Override
    public void execute() {
        System.out.println("""

                Serial
                --------------------
                """);
        System.out.println("Sending ...");
        var msg = this.room.getHistory().lastEntry().getValue();
        var send = (msg == null ? "" : msg.getKey() ? "ON" : "OFF") + "/" + msg.getValue();
        channel.sendMsg(send);

        try {
            System.out.println("Receiving ...");
            var receive = Arrays.stream(this.channel.receiveMsg().split("/")).toList();

            boolean lights = receive.get(0).equals("ON");
            int rollerBlinds = Integer.parseInt(receive.get(1));
            this.room.setState(Optional.of(Pair.of(lights, rollerBlinds)), this.priority);

            Thread.sleep(500);
        } catch (InterruptedException ignored) {}
    }

}
