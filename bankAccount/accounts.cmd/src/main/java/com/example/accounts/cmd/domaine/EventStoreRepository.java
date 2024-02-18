package com.example.accounts.cmd.domaine;

import com.example.sqrs.core.commands.BaseCommand;
import com.example.sqrs.core.events.EventModel;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventStoreRepository extends MongoRepository<EventModel,String> {
    List<EventModel> findByAggregateIdentifier(String aggregateIdentifier);
}
