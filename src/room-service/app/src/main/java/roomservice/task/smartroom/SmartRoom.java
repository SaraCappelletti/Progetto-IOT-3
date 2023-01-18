package roomservice.task.smartroom;

public interface SmartRoom {

    boolean isLightOn();

    void setLightOn(final boolean lightOn);

    int getRollerBlindsUnrollmentPercentage();

    void setRollerBlindsUnrollmentPercentage(final int rollerBlindsUnrollmentPercentage);

    void setTempValues(final boolean lightOn, final int rollerBlindsUnrollmentPercentage);

}
