package io.sankha.skilltracker.cmd.infrastructure;

import io.sankha.skilltracker.cmd.domain.EventStoreRepository;
import io.sankha.skilltracker.cqrs.core.events.BaseEvent;
import io.sankha.skilltracker.cqrs.core.events.EventModel;
import io.sankha.skilltracker.cqrs.core.exceptions.AggregateNotFoundException;
import io.sankha.skilltracker.cqrs.core.exceptions.ConcurrencyException;
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
class SkillTrackerEventStoreTests {

  @InjectMocks SkillTrackerEventStore eventStore;
  @Mock EventProducer eventProducer;
  @Mock EventStoreRepository eventStoreRepository;

  @Test
  void saveEventsTest() {
    var eventList = List.of(EventModel.builder().build());
    Mockito.when(this.eventStoreRepository.findByAggregateIdentifier(ArgumentMatchers.any()))
        .thenReturn(eventList);

    Mockito.when(this.eventStoreRepository.save(ArgumentMatchers.any()))
        .thenReturn(EventModel.builder().id("1").build());

    Mockito.doNothing()
        .when(this.eventProducer)
        .produce(ArgumentMatchers.anyString(), ArgumentMatchers.any());

    List<BaseEvent> events = List.of(ProfileAddedEvent.builder().id("").build());

    eventStore.saveEvents("1", events, -1, "");

    Mockito.verify(eventProducer, Mockito.times(1))
        .produce("ProfileAddedEvent", ProfileAddedEvent.builder().id("1").build());
  }

  @Test
  void saveEventsExceptionTest() {
    var eventList = List.of(EventModel.builder().build());
    Mockito.when(this.eventStoreRepository.findByAggregateIdentifier(ArgumentMatchers.any()))
        .thenReturn(eventList);

    ConcurrencyException thrown =
        Assertions.assertThrows(
            ConcurrencyException.class, () -> eventStore.saveEvents("1", null, 1, ""));

    Assertions.assertNotNull(thrown);
  }

  @Test
  void getEventsTest() {
    var eventList = List.of(EventModel.builder().build());
    Mockito.when(this.eventStoreRepository.findByAggregateIdentifier(ArgumentMatchers.any()))
        .thenReturn(eventList);
    var storeEvents = eventStore.getEvents("");
    Assertions.assertNotNull(storeEvents);
  }

  @Test
  void getEventsExceptionTest() {
    Mockito.when(this.eventStoreRepository.findByAggregateIdentifier(ArgumentMatchers.any()))
        .thenReturn(new ArrayList<>());
    AggregateNotFoundException thrown =
        Assertions.assertThrows(
            AggregateNotFoundException.class, () -> eventStore.getEvents(""), "Expected Exception");

    Assertions.assertTrue(thrown.getMessage().contains("Incorrect employee ID provided!"));
  }

  @Test
  void getAggregateIdsTest() {
    var eventList = List.of(EventModel.builder().build());
    Mockito.when(this.eventStoreRepository.findAll()).thenReturn(eventList);
    var storeEvents = eventStore.getAggregateIds();
    Assertions.assertNotNull(storeEvents);
  }

  @Test
  void getAggregateIdsTestExceptionTest() {
    Mockito.when(this.eventStoreRepository.findAll()).thenReturn(new ArrayList<>());
    IllegalStateException thrown =
        Assertions.assertThrows(
            IllegalStateException.class, () -> eventStore.getAggregateIds(), "Expected Exception");

    Assertions.assertTrue(
        thrown.getMessage().contains("Could not retrieve event stream from the event store!"));
  }
}
