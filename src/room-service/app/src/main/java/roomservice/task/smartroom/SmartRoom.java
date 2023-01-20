package roomservice.task.smartroom;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;


public interface SmartRoom {

//    boolean isLightOn();

//    int getRollerBlindsUnrollmentPercentage();

    void setState(final Optional<Pair<Boolean, Integer>> currState, final int priorityLevel);

    Map<String, Pair<Boolean, Integer>> getHistory();

    String getHistoryAsJsonString();
    
}
