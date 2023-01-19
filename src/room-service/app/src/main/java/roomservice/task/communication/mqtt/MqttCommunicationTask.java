package roomservice.task.communication.mqtt;

import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttServer;

import roomservice.task.smartroom.SmartRoom;
import roomservice.task.Task;

public class MqttCommunicationTask implements Task {

	final SmartRoom room;
	private final int priority;

	public MqttCommunicationTask(final SmartRoom room, final int priorityLevel) {
		this.room = room;
		this.priority = priorityLevel;

		Vertx vertx = Vertx.vertx();
		MqttServer mqttServer = MqttServer.create(vertx);
		mqttServer.endpointHandler(endpoint -> {

					// shows main connect info
					System.out.println("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());

					if (endpoint.auth() != null) {
						System.out.println("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
					}
					if (endpoint.will() != null) {
						System.out.println("[will topic = " + endpoint.will().getWillTopic() + " msg = " + new String(endpoint.will().getWillMessageBytes()) +
								" QoS = " + endpoint.will().getWillQos() + " isRetain = " + endpoint.will().isWillRetain() + "]");
					}

					System.out.println("[keep alive timeout = " + endpoint.keepAliveTimeSeconds() + "]");

					// accept connection from the remote client
					endpoint.accept(false);

				})
				.listen(ar -> {

					if (ar.succeeded()) {

						System.out.println("MQTT server is listening on port " + ar.result().actualPort());
					} else {

						System.out.println("Error on starting the server");
						ar.cause().printStackTrace();
					}
				});
	}

	@Override
	public void execute() {

	}

}
