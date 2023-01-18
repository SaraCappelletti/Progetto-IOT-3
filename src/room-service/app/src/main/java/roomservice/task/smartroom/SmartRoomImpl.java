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

    public SmartRoomImpl() {
        this.dateHourHistory = new LinkedList<>();
        this.currLight = false;
        this.currRollerBlindsUnrollmentPercentage = 100;
    }

    public synchronized boolean isLightOn() {
        return this.currLight;
    }

    public synchronized void setLightOn(final boolean lightOn) {
        this.currLight = lightOn;
    }

    public synchronized int getRollerBlindsUnrollmentPercentage() {
        return this.currRollerBlindsUnrollmentPercentage;
    }

    public synchronized void setRollerBlindsUnrollmentPercentage(final int rollerBlindsUnrollmentPercentage) {
        this.currRollerBlindsUnrollmentPercentage = rollerBlindsUnrollmentPercentage;
    }

    @Override
    public void setTempValues(final boolean lightOn, final int rollerBlindsUnrollmentPercentage) {
        this.currLight = lightOn;
        this.currRollerBlindsUnrollmentPercentage = rollerBlindsUnrollmentPercentage;
    }

    @Override
    public void execute() {
        this.dateHourHistory.add(
            Pair.of(
                DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm:ss").format(LocalDateTime.now()),
                this.currLight ? "ON" : "OFF" + "/" + this.currRollerBlindsUnrollmentPercentage
            )
        );
    }

}
