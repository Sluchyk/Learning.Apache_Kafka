package com.example.sqrs.core.handlers;

import com.example.sqrs.core.domaine.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregateRoot);
    T getById(String id);
    void republishEvents();
}
