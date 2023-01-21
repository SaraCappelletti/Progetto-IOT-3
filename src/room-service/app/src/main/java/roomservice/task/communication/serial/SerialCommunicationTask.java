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
        var msg = this.room.getHistory().lastEntry().getValue();
        var send = (msg == null ? "" : msg.getKey() ? "ON" : "OFF") + "/" + msg.getValue();
        System.out.println("Sending "+send);
        channel.sendMsg(send);

        try {
            var receive = Arrays.stream(this.channel.receiveMsg().split("/")).toList();
            System.out.println("Receiving " + receive);

            boolean lights = receive.get(0).equals("ON");
            int rollerBlinds = Integer.parseInt(receive.get(1));
            this.room.setState(Optional.of(Pair.of(lights, rollerBlinds)), this.priority);
        } catch (InterruptedException ignored) {}
    }

}
