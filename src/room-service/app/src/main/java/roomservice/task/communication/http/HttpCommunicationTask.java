package roomservice.task.communication.http;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import roomservice.task.smartroom.SmartRoom;
import roomservice.task.Task;

import java.util.Map;
import java.util.stream.Collectors;

public class HttpCommunicationTask implements Task {

    final SmartRoom room;
    int i;
    private String s;
    private final int priority;

    public HttpCommunicationTask(final SmartRoom room, final int priorityLevel) throws RuntimeException {
        this.room = room;
        this.priority = priorityLevel;

        final Vertx vertx = Vertx.vertx();
        final Router router = Router.router(vertx);
        router.route()
                .handler(BodyHandler.create())
                .handler(req -> {

//                    {
//                        "2022-01-19T10:05:22.123": {
//                            "light": "ON",
//                            "rollerBlind": 30
//                        },
//                        "2022-01-19T10:05:21.123": {
//                            "light": "ON",
//                            "rollerBlind": 50
//                        },
//                        "2022-01-19T10:05:20.123": {
//                            "light": "OFF",
//                            "rollerBlind": 70
//                        },
//                        "2022-01-19T10:05:19.123": {
//                            "light": "ON",
//                            "rollerBlind": 20
//                        }
//                    }


                    req.request().params().forEach((n, v) -> System.out.println(n + ": " + v));
                    req.json(new JsonObject(req.request().params()
                            .entries().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
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
//light -> ON/OFF       angle -> 0-100
    @Override
    public void execute() {
//        System.out.println(i++ + s);
    }

}
