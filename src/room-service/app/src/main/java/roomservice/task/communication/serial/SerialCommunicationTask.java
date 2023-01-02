package roomservice.task.communication.serial;

import roomservice.task.Task;

public class SerialCommunicationTask implements Task {

    final CommChannel channel;

    public SerialCommunicationTask(final String port) throws SerialCommunicationTaskException {
        try {
            this.channel = new SerialCommChannel(port, 9600);
        } catch (Exception e) {
            throw new SerialCommunicationTaskException();
        }
    }

    @Override
    public void execute() {
        System.out.println("Serial\n" +
                "--------------------\n");
        System.out.println("Sending ping");
        channel.sendMsg("ping");
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
