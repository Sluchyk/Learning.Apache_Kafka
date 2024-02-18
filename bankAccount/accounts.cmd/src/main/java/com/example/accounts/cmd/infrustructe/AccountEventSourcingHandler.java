package com.example.accounts.cmd.infrustructe;

import com.example.accounts.cmd.domaine.AccountAggregate;
import com.example.sqrs.core.domaine.AggregateRoot;
import com.example.sqrs.core.handlers.EventSourcingHandler;
import com.example.sqrs.core.infrastructure.EventStore;
import com.example.sqrs.core.producers.EventProducer;
import java.util.Comparator;
import org.springframework.stereotype.Service;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;
    private final EventProducer eventProducer;
    public AccountEventSourcingHandler(EventStore eventStore, EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AggregateRoot aggregateRoot) {
        eventStore.saveEventsStore(aggregateRoot.getId(),
                aggregateRoot.getUncommittedChanges(),
                aggregateRoot.getVersion());
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if(events != null && !events.isEmpty()){
            aggregate.replayEvent(events);
            var latestVersion = events.stream().map(x->x.getVersion()).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds){
            var aggregate  = getById(aggregateId);
            if (aggregate == null || !aggregate.getIsActive()) continue;
            var events = eventStore.getEvents(aggregateId);
            for (var event :events){
                eventProducer.produce(event.getClass().getSimpleName(),event);
            }
        }
    }
}
