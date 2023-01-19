package roomservice.task.smartroom;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NavigableMap;
import java.util.StringJoiner;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.Pair;

import roomservice.task.Task;

public class SmartRoomImpl implements SmartRoom, Task {

    // Does not manage list-full
    private final NavigableMap<String, Pair<Boolean, Integer>> dateHourHistory;

    private boolean currLight;
    private int currRollerBlindsUnrollmentPercentage;
    private int lastPriorityLevel;

    public SmartRoomImpl() {
        this.dateHourHistory = new TreeMap<>();
        this.currLight = false;
        this.currRollerBlindsUnrollmentPercentage = 100;
        this.lastPriorityLevel = 0;
    }

    public synchronized boolean isLightOn() {
        return this.currLight;
    }

    public synchronized int getRollerBlindsUnrollmentPercentage() {
        return this.currRollerBlindsUnrollmentPercentage;
    }

    @Override
    public synchronized boolean setState(final boolean lightOn, final int rollerBlindsUnrollmentPercentage, final int priorityLevel) {
        if (priorityLevel < this.lastPriorityLevel)
            return false;

        if (this.isLightOn() == lightOn && this.getRollerBlindsUnrollmentPercentage() == rollerBlindsUnrollmentPercentage)
            return false;

        this.lastPriorityLevel = priorityLevel;
        this.currLight = lightOn;
        this.currRollerBlindsUnrollmentPercentage = rollerBlindsUnrollmentPercentage;
        System.out.println(this.currLight + " " + this.currRollerBlindsUnrollmentPercentage);
        return true;
    }

    @Override
    public synchronized NavigableMap<String, Pair<Boolean, Integer>> getHistory() {
        return new TreeMap<>(this.dateHourHistory);
    }

    @Override
    public synchronized String getHistoryAsJsonString() {
        System.out.println(this.dateHourHistory.size());
        StringBuilder json = new StringBuilder("{");
        StringJoiner sj = new StringJoiner(",");
        dateHourHistory.forEach((k, v) -> {
            sj.add("\"" + k + "\":" + "{" + "\"light\":" + "\"" + (v.getKey() ? "ON" : "OFF") + "\"" + "," + "\"rollerBlind\":" + v.getValue() + "}");
        });
        json.append(sj.toString()).append("}");
        return json.toString();
    }

    @Override
    public void execute() {
        this.lastPriorityLevel = 0;
        this.addToHistory(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(LocalDateTime.now()), this.getTempState());
//        this.dateHourHistory.add(Pair.of(
//            DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss").format(
//                    LocalDateTime.now()
//            ),
//            this.getTempState()
//        ));
    }

    @Override
    public String toString() {
        if (this.dateHourHistory.isEmpty())
            return "";
        var tmp = this.dateHourHistory.lastEntry().getValue();
        return tmp.getKey() ? "ON" : "OFF" + "/" + tmp.getValue();
    }

    private synchronized void addToHistory(final String time, final Pair<Boolean, Integer> state) {
        this.dateHourHistory.put(time, state);
    }

    private Pair<Boolean, Integer> getTempState() {
        return Pair.of(this.currLight, this.currRollerBlindsUnrollmentPercentage);
    }

}
