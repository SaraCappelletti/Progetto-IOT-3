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
    private Optional<Pair<Boolean, Integer>> currState = Optional.empty();
    private int lastPriorityLevel;

    public SmartRoomImpl() {
        this.currState = Optional.of(Pair.of(false, 100));
        this.dateHourHistory = new TreeMap<>();
        this.execute();
    }

    @Override
    public synchronized void setState(final Optional<Pair<Boolean, Integer>> state, final int priorityLevel) {
        if (priorityLevel != 0)  return;
        if (state.isEmpty() || priorityLevel < this.lastPriorityLevel)
            return;

        if (this.currState.isPresent() && (this.currState.get().getKey() == state.get().getKey() && this.currState.get().getValue() == state.get().getValue()))
            return;

        this.lastPriorityLevel = priorityLevel;
        this.currState = state;
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
        this.lastPriorityLevel = 0;
        var state = this.currState;
        this.setState(Optional.empty(), lastPriorityLevel);
        if (state.isPresent()) {
            this.addToHistory(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(LocalDateTime.now()), state.get());
            System.out.println((this.getHistory().lastEntry().getValue().getKey() ? "ON" : "OFF")+ "/" + this.getHistory().lastEntry().getValue().getValue());
        }
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

//    private synchronized Pair<Boolean, Integer> getTempState() {
//        return Pair.of(this.currLight, this.currRollerBlindsUnrollmentPercentage);
//    }
//
}
