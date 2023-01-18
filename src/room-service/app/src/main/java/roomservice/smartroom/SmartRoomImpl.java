package roomservice.smartroom;

import java.util.Map;

public class SmartRoomImpl implements SmartRoom {

    private boolean isLightOn;
    private int rollerBlindsUnrollmentPercentage;

    public SmartRoomImpl() {
        this.isLightOn = false;
        this.rollerBlindsUnrollmentPercentage = 0;
    }

    public synchronized boolean isLightOn() {
        return this.isLightOn;
    }

    public synchronized void setLightOn(final boolean lightOn) {
        this.isLightOn = lightOn;
    }

    public synchronized int getRollerBlindsUnrollmentPercentage() {
        return this.rollerBlindsUnrollmentPercentage;
    }

    public synchronized void setRollerBlindsUnrollmentPercentage(int rollerBlindsUnrollmentPercentage) {
        this.rollerBlindsUnrollmentPercentage = rollerBlindsUnrollmentPercentage;
    }

}
