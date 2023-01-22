package roomservice.task.smartroom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.Pair;

import roomservice.task.Task;

public class SmartRoomImpl implements SmartRoom, Task {

    // Does not manage list-full
    private final NavigableMap<String, Pair<Boolean, Integer>> dateHourHistory;
    private Pair<Boolean, Integer> currState;
    private int lastPriorityLevel;

    public SmartRoomImpl() {
        this.dateHourHistory = new TreeMap<>();
        this.currState = Pair.of(false, 100);
        this.execute();
    }

    @Override
    public synchronized void setState(final Pair<Boolean, Integer> state, final int priorityLevel) {
//        if (priorityLevel == 2)  return;
        if (priorityLevel < this.lastPriorityLevel)
            return;

        if (this.currState.getKey() == state.getKey() && this.currState.getValue() == state.getValue())
            return;

        System.out.println((priorityLevel == 0 ? "MQTT" : priorityLevel == 1 ? "HTTP" : "Serial") + " " + state);
        this.lastPriorityLevel = priorityLevel;
        this.currState = state;
    }

    public synchronized Pair<Boolean, Integer> getCurrState() {
        return this.currState;
    }

    @Override
    public synchronized NavigableMap<String, Pair<Boolean, Integer>> getHistory() {
        return new TreeMap<>(this.dateHourHistory);
    }

    @Override
    public String getHistoryAsJsonString() {
        var json = new StringBuilder("{");
        var sj = new StringJoiner(",");
        this.getHistory().forEach((k, v) -> sj.add("\"" + k + "\":" + "{" + "\"light\":" + "\"" + (v.getKey() ? "ON" : "OFF") + "\"" + "," + "\"rollerBlind\":" + v.getValue() + "}"));
        json.append(sj).append("}");
        return json.toString();
    }

    @Override
    public void execute() {
        System.out.println("Stato: " + this.getCurrState() + "\n");
        this.lastPriorityLevel = 0;
        var state = this.currState;
        this.addToHistory(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(LocalDateTime.now()), state);
//        System.out.println((this.getHistory().lastEntry().getValue().getKey() ? "ON" : "OFF")+ "/" + this.getHistory().lastEntry().getValue().getValue());
//        System.out.println("----------------------");
    }

    @Override
    public String toString() {
        if (this.getHistory().isEmpty())
            return "";
        var tmp = this.getHistory().lastEntry().getValue();
        return tmp.getKey() ? "ON" : "OFF" + "/" + tmp.getValue();
    }

    private synchronized void addToHistory(final String time, final Pair<Boolean, Integer> state) {
        this.dateHourHistory.put(time, state);
    }

}
