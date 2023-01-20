package roomservice.task.communication.http;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import roomservice.task.smartroom.SmartRoom;
import roomservice.task.Task;

public class HttpCommunicationTask implements Task {

    final SmartRoom room;
    private final int priority;
    private boolean light;
    private int rollerBlind;

    public HttpCommunicationTask(final SmartRoom room, final int priorityLevel) throws RuntimeException {
        this.room = room;
        this.priority = priorityLevel;
        this.light = false;
        this.rollerBlind = 100;

        final Vertx vertx = Vertx.vertx();
        final Router router = Router.router(vertx);
        router.route()
                .handler(BodyHandler.create())
                .handler(req -> {
                    var params = req.request().params();
                    if (params.contains("light") && params.contains("rollerBlind")) {
                        var light = params.get("light");
                        if (light.equals("ON") || light.equals("OFF")) {
                            try {
                                this.setLight(light.equals("ON"));
                                this.setRollerBlind(Integer.parseInt(params.get("rollerBlind")));
                            } catch (Exception ignored) {}
                        }
                    }
                    req.json(new JsonObject(this.room.getHistoryAsJsonString()));
                });

        // Create the HTTP server
        vertx.createHttpServer()
                // Handle every request
                .requestHandler(router)
                // Start listening
                .listen(8088)
                // Print the port
                .onSuccess(server -> System.out.println("HTTP on http://localhost:" + server.actualPort() +
                                                        "\n--------------------\n"))
                .onFailure(server -> {
                    throw new RuntimeException("Unable to create Serial-Communication Task");
                });
    }

    @Override
    public void execute() {
        this.room.setState(this.getLight(), this.getRollerBlind(), this.priority);
    }

    private synchronized boolean getLight() {
        return this.light;
    }

    private synchronized void setLight(final boolean light) {
        this.light = light;
    }

    private synchronized int getRollerBlind() {
        return this.rollerBlind;
    }

    private synchronized void setRollerBlind(final int rollerBlind) {
        this.rollerBlind = rollerBlind;
    }

}
