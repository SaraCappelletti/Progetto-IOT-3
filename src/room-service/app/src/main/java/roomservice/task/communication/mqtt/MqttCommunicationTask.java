package roomservice.task.communication.mqtt;

import java.util.Optional;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import io.vertx.mqtt.MqttServer;

import io.vertx.mqtt.MqttTopicSubscription;
import org.apache.commons.lang3.tuple.Pair;
import roomservice.task.smartroom.SmartRoom;
import roomservice.task.Task;


public class MqttCommunicationTask implements Task {

	final SmartRoom room;
	private final int priority;
	private final String mqtt_server = "broker.mqtt-dashboard.com";
	private final String topic = "Leonardo";
    private final int port = 1883;
    private Pair<Boolean, Integer> localState;

    public MqttCommunicationTask(final SmartRoom room, final int priorityLevel) {
        this.room = room;
        this.priority = priorityLevel;
        this.localState = Pair.of(false, 100);

        Vertx vertx = Vertx.vertx();
        MqttClient mqttClient = MqttClient.create(vertx, new MqttClientOptions()
                .setAutoKeepAlive(true)
                .setClientId("room-service")
        );
        mqttClient.connect(this.port, this.mqtt_server, conn -> {
            if (conn.failed()) {
                throw new RuntimeException("Unable to create Mqtt-Communication Task");
            }
            mqttClient.subscribe(this.topic, 0, sub -> {}).publishHandler(message -> {
                try {
                    JsonObject received = new JsonObject(message.payload().toString());
                    boolean light = received.getBoolean("pirState");
                    int rollerBlind = received.getInteger("lumValue");
                    this.setLocalState(Pair.of(light, rollerBlind));
                } catch (Exception ignored) {}
            });
        });
	}

    public void execute() {
        var state = this.getLocalState();
//        System.out.println("Mqtt " + state);
        this.room.setState(state, this.priority);
    }

    private synchronized void setLocalState(final Pair<Boolean, Integer> state) {
        this.localState = state;
    }

    private synchronized Pair<Boolean, Integer> getLocalState() {
        return this.localState;
    }

}
