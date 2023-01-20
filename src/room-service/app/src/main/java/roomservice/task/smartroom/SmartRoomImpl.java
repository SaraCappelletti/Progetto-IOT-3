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
        this.dateHourHistory = new TreeMap<>();
        this.currState = Optional.of(Pair.of(false, 100));
        this.lastPriorityLevel = 0;
    }

//    private synchronized boolean isLightOn() {
//        if (this.currState.isPresent()) {
//            return this.currState.get().getKey();
//        }
//        return false;
//    }
//
//    private synchronized int getRollerBlindsUnrollmentPercentage() {
//        return this.currRollerBlindsUnrollmentPercentage;
//    }

    @Override
    public synchronized void setState(final Optional<Pair<Boolean, Integer>> state, final int priorityLevel) {
        if (state.isEmpty() || priorityLevel < this.lastPriorityLevel)
            return;

        if (this.currState.get().getKey() == state.get().getKey() && this.currState.get().getValue() == state.get().getValue())
            return;

        this.lastPriorityLevel = priorityLevel;
        this.currState = state;
//        return true;
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
        var state = this.currState;
        if (state.isPresent()) {
            this.addToHistory(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(LocalDateTime.now()), state.get());
            System.out.println((this.getHistory().lastEntry().getValue().getKey() ? "ON" : "OFF")+ "/" + this.getHistory().lastEntry().getValue().getValue() + (this.lastPriorityLevel == 0 ? " -> mqtt" : this.lastPriorityLevel == 1 ? " -> http" : " -> serial"));
        }
        this.lastPriorityLevel = 0;
        this.setState(Optional.empty(), lastPriorityLevel);
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
