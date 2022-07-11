package io.sankha.skilltracker.cqrs.core.handlers;

import io.sankha.skilltracker.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
  void save(AggregateRoot aggregate);

  T getById(String id);

  void republishEvents();
}
