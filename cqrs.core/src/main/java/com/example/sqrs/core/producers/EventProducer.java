package com.example.sqrs.core.producers;

import com.example.sqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
