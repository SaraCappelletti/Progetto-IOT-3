package roomservice.smartroom;

public interface SmartRoom {

    boolean isLightOn();

    void setLightOn(final boolean lightOn);

    int getRollerBlindsUnrollmentPercentage();

    void setRollerBlindsUnrollmentPercentage(int rollerBlindsUnrollmentPercentage);

}
