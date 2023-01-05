package roomservice.task.communication.serial;

import roomservice.smartroom.SmartRoom;
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
        // EMPTY || [ ON|OFF ]/[0-100]

        System.out.println("\nSerial\n" +
                "--------------------\n");
        System.out.println("Sending OFF/0");
        channel.sendMsg("OFF/0");
        String msg = null;
        try {
            msg = this.channel.receiveMsg();
            System.out.println("Received: "+msg);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (InterruptedException e) {
        }
    }

}
