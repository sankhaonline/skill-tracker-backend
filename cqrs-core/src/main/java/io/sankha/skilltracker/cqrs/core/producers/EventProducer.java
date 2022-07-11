package io.sankha.skilltracker.cqrs.core.producers;

import io.sankha.skilltracker.cqrs.core.events.BaseEvent;

public interface EventProducer {
  void produce(String topic, BaseEvent event);
}
