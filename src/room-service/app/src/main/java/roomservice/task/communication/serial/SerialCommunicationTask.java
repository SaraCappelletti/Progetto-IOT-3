package roomservice.task.communication.serial;

import roomservice.task.smartroom.SmartRoom;
import roomservice.task.Task;

public class SerialCommunicationTask implements Task {

    final CommChannel channel;
    final SmartRoom room;

    public SerialCommunicationTask(final String port, final SmartRoom room) throws Exception {
        try {
            this.channel = new SerialCommChannel(port, 9600);
        } catch (Exception e) {
            throw new  Exception("Unable to create Serial-Communication Task");
        }
        this.room = room;
    }

    @Override
    public void execute() {
        // [ ON|OFF ]/[0-100]
        // var tmp = stato mandato
        // if recv == tmp
        //
        System.out.println("\nSerial\n" +
                "--------------------\n");
        System.out.println("Sending OFF/0");
        channel.sendMsg(this.room.toString());
        String msg = null;
        try {
            msg = this.channel.receiveMsg();
            System.out.println("Received: " + msg);

            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

}
