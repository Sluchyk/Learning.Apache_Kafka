package com.example.accounts.cmd.infrustructe;

import com.example.accounts.cmd.domaine.AccountAggregate;
import com.example.accounts.cmd.domaine.EventStoreRepository;
import com.example.sqrs.core.events.BaseEvent;
import com.example.sqrs.core.events.EventModel;
import com.example.sqrs.core.exception.AggregateNotFoundException;
import com.example.sqrs.core.exception.ConcurrencyException;
import com.example.sqrs.core.infrastructure.EventStore;
import com.example.sqrs.core.producers.EventProducer;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AccountEventStore implements EventStore {
    private final EventStoreRepository eventStoreRepository;
private final EventProducer eventProducer;
    public AccountEventStore(EventStoreRepository eventStoreRepository, EventProducer eventProducer) {
        this.eventStoreRepository = eventStoreRepository;
        this.eventProducer = eventProducer;
    }

    @Override
    public void saveEventsStore(String aggregateId, Iterable<BaseEvent> events,int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion != -1 && eventStream.get(eventStream.size()-1).getVersion()!= expectedVersion){
            throw  new ConcurrencyException();
        }
        var version = expectedVersion;
        for(var event : events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timestamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if(!persistedEvent.getId().isEmpty()){
                eventProducer.produce(event.getClass().getSimpleName(),event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(eventStream == null || eventStream.isEmpty()){
            throw  new AggregateNotFoundException("Incorrect account id provided");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if(eventStream == null || eventStream.isEmpty()){
            throw new IllegalStateException("Could not retrieve event store from event store");
        }
        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }
}
