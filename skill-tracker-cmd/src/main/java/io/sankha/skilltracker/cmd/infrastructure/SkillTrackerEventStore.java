package io.sankha.skilltracker.cmd.infrastructure;

import io.sankha.skilltracker.cmd.domain.EventStoreRepository;
import io.sankha.skilltracker.cqrs.core.events.BaseEvent;
import io.sankha.skilltracker.cqrs.core.events.EventModel;
import io.sankha.skilltracker.cqrs.core.exceptions.AggregateNotFoundException;
import io.sankha.skilltracker.cqrs.core.exceptions.ConcurrencyException;
import io.sankha.skilltracker.cqrs.core.infrastructure.EventStore;
import io.sankha.skilltracker.cqrs.core.producers.EventProducer;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SkillTrackerEventStore implements EventStore {

  private final EventProducer eventProducer;
  private final EventStoreRepository eventStoreRepository;

  @Override
  public void saveEvents(
      String aggregateId, Iterable<BaseEvent> events, int expectedVersion, String aggregateType) {
    var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
    if (expectedVersion != -1
        && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
      throw new ConcurrencyException();
    }
    var version = expectedVersion;
    for (var event : events) {
      version++;
      event.setVersion(version);
      var eventModel =
          EventModel.builder()
              .timeStamp(new Date())
              .aggregateIdentifier(aggregateId)
              .aggregateType(aggregateType)
              .version(version)
              .eventType(event.getClass().getTypeName())
              .eventData(event)
              .build();
      var persistedEvent = eventStoreRepository.save(eventModel);
      if (!persistedEvent.getId().isEmpty()) {
        eventProducer.produce(event.getClass().getSimpleName(), event);
      }
    }
  }

  @Override
  public List<BaseEvent> getEvents(String aggregateId) {
    var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
    if (CollectionUtils.isEmpty(eventStream)) {
      throw new AggregateNotFoundException("Incorrect employee ID provided!");
    }
    return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
  }

  @Override
  public List<String> getAggregateIds() {
    var eventStream = eventStoreRepository.findAll();
    if (CollectionUtils.isEmpty(eventStream)) {
      throw new IllegalStateException("Could not retrieve event stream from the event store!");
    }
    return eventStream.stream()
        .map(EventModel::getAggregateIdentifier)
        .distinct()
        .collect(Collectors.toList());
  }
}
