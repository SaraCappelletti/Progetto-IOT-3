package roomservice.task.smartroom;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;


public interface SmartRoom {

    boolean isLightOn();

    int getRollerBlindsUnrollmentPercentage();

    boolean setState(final boolean lightOn, final int rollerBlindsUnrollmentPercentage, final int priorityLevel);

    Map<String, Pair<Boolean, Integer>> getHistory();

    String getHistoryAsJsonString();
    
}
