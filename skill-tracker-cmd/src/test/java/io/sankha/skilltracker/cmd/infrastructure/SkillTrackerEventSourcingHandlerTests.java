package io.sankha.skilltracker.cmd.infrastructure;

import io.sankha.skilltracker.cmd.domain.SkillTrackerAggregate;
import io.sankha.skilltracker.cqrs.core.events.BaseEvent;
import io.sankha.skilltracker.cqrs.core.infrastructure.EventStore;
import io.sankha.skilltracker.cqrs.core.producers.EventProducer;
import io.sankha.skilltracker.profile.events.ProfileAddedEvent;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SkillTrackerEventSourcingHandlerTests {

  @InjectMocks SkillTrackerEventSourcingHandler commandHandler;
  @Mock EventStore eventStore;
  @Mock EventProducer eventProducer;

  @Test
  void saveTest() {
    Mockito.doNothing()
        .when(this.eventStore)
        .saveEvents(
            ArgumentMatchers.any(),
            ArgumentMatchers.any(),
            ArgumentMatchers.anyInt(),
            ArgumentMatchers.any());
    commandHandler.save(new SkillTrackerAggregate());
    Mockito.verify(eventStore, Mockito.times(1))
        .saveEvents(
            null, new ArrayList<>(), -1, "io.sankha.skilltracker.cmd.domain.SkillTrackerAggregate");
  }

  @Test
  void getByIdTest() {
    List<BaseEvent> eventList = List.of(ProfileAddedEvent.builder().build());
    Mockito.when(this.eventStore.getEvents(ArgumentMatchers.any())).thenReturn(eventList);
    var SkillTrackerAggregate = commandHandler.getById("");
    Assertions.assertNotNull(SkillTrackerAggregate);
  }

  @Test
  void republishEventsTest() {
    Mockito.when(this.eventStore.getAggregateIds()).thenReturn(List.of(""));

    List<BaseEvent> eventList = List.of(ProfileAddedEvent.builder().build());
    Mockito.when(this.eventStore.getEvents(ArgumentMatchers.any()))
        .thenReturn(eventList)
        .thenReturn(eventList);

    Mockito.doNothing()
        .when(this.eventProducer)
        .produce(ArgumentMatchers.anyString(), ArgumentMatchers.any());

    commandHandler.republishEvents();
    Mockito.verify(eventProducer, Mockito.times(1))
        .produce("ProfileAddedEvent", ProfileAddedEvent.builder().build());
  }
}
