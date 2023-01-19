package roomservice.task.smartroom;

import org.apache.commons.lang3.tuple.Pair;
import roomservice.task.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class SmartRoomImpl implements SmartRoom, Task {

    // Does not manage list-full
    private final List<Pair<String, String>> dateHourHistory;

    private boolean currLight;
    private int currRollerBlindsUnrollmentPercentage;
    private int lastPriorityLevel;

    public SmartRoomImpl() {
        this.dateHourHistory = new LinkedList<>();
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

        return true;
    }

    @Override
    public void execute() {
        this.dateHourHistory.add(Pair.of(
            DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss").format(LocalDateTime.now()),
            this.getTempState()
        ));
    }

    @Override
    public String toString() {
        return this.dateHourHistory.isEmpty() ? "" :
                this.dateHourHistory.get(this.dateHourHistory.size() - 1).getValue();
    }

    private String getTempState() {
        return this.currLight ? "ON" : "OFF" + "/" + this.currRollerBlindsUnrollmentPercentage;
    }

}
