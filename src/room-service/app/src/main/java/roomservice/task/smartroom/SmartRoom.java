package roomservice.task.smartroom;

import java.util.NavigableMap;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;


public interface SmartRoom {

    void setState(final Optional<Pair<Boolean, Integer>> currState, final int priorityLevel);

    NavigableMap<String, Pair<Boolean, Integer>> getHistory();

    String getHistoryAsJsonString();
    
}
