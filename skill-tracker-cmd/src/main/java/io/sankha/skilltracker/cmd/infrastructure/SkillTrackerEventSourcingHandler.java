package io.sankha.skilltracker.cmd.infrastructure;

import io.sankha.skilltracker.cmd.domain.SkillTrackerAggregate;
import io.sankha.skilltracker.cqrs.core.domain.AggregateRoot;
import io.sankha.skilltracker.cqrs.core.events.BaseEvent;
import io.sankha.skilltracker.cqrs.core.handlers.EventSourcingHandler;
import io.sankha.skilltracker.cqrs.core.infrastructure.EventStore;
import io.sankha.skilltracker.cqrs.core.producers.EventProducer;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SkillTrackerEventSourcingHandler
    implements EventSourcingHandler<SkillTrackerAggregate> {

  private final EventStore eventStore;
  private final EventProducer eventProducer;

  @Override
  public void save(AggregateRoot aggregate) {
    eventStore.saveEvents(
        aggregate.getId(),
        aggregate.getUncommittedChanges(),
        aggregate.getVersion(),
        SkillTrackerAggregate.class.getTypeName());
    aggregate.markChangesAsCommitted();
  }

  @Override
  public SkillTrackerAggregate getById(String id) {
    var aggregate = new SkillTrackerAggregate();
    var events = eventStore.getEvents(id);
    if (CollectionUtils.isEmpty(events)) {
      aggregate.replayEvents(events);
      var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
      aggregate.setVersion(latestVersion.orElse(-1));
    }
    return aggregate;
  }

  @Override
  public void republishEvents() {
    var aggregateIds = eventStore.getAggregateIds();
    for (var aggregateId : aggregateIds) {
      var aggregate = getById(aggregateId);
      if (aggregate == null) continue;
      var events = eventStore.getEvents(aggregateId);
      for (var event : events) {
        eventProducer.produce(event.getClass().getSimpleName(), event);
      }
    }
  }
}
