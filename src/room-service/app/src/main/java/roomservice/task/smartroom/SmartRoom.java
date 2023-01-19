package roomservice.task.smartroom;

public interface SmartRoom {

    boolean isLightOn();

    int getRollerBlindsUnrollmentPercentage();

    boolean setState(final boolean lightOn, final int rollerBlindsUnrollmentPercentage, final int priorityLevel);

}
