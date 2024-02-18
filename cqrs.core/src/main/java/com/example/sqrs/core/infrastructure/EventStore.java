package com.example.sqrs.core.infrastructure;

import com.example.sqrs.core.events.BaseEvent;
import java.util.List;

public interface EventStore {
    void saveEventsStore(
            String aggregateId,
            Iterable<BaseEvent> events,
            int expectedVersion);

    List<BaseEvent> getEvents(String aggregateId);
    List<String> getAggregateIds();;
}
