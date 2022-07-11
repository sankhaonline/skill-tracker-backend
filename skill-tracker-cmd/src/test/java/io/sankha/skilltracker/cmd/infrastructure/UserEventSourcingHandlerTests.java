package io.sankha.skilltracker.cmd.infrastructure;

import io.sankha.skilltracker.cmd.domain.UserAggregate;
import io.sankha.skilltracker.cqrs.core.events.BaseEvent;
import io.sankha.skilltracker.cqrs.core.infrastructure.EventStore;
import io.sankha.skilltracker.cqrs.core.producers.EventProducer;
import io.sankha.skilltracker.user.events.UserRegisteredEvent;
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
class UserEventSourcingHandlerTests {

  @InjectMocks UserEventSourcingHandler commandHandler;
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
    commandHandler.save(new UserAggregate());
    Mockito.verify(eventStore, Mockito.times(1))
        .saveEvents(null, new ArrayList<>(), -1, "io.sankha.skilltracker.cmd.domain.UserAggregate");
  }

  @Test
  void getByIdTest() {
    List<BaseEvent> eventList = List.of(UserRegisteredEvent.builder().build());
    Mockito.when(this.eventStore.getEvents(ArgumentMatchers.any())).thenReturn(eventList);
    var userAggregate = commandHandler.getById("");
    Assertions.assertNotNull(userAggregate);
  }

  @Test
  void republishEventsTest() {
    Mockito.when(this.eventStore.getAggregateIds()).thenReturn(List.of(""));

    List<BaseEvent> eventList = List.of(UserRegisteredEvent.builder().build());
    Mockito.when(this.eventStore.getEvents(ArgumentMatchers.any()))
        .thenReturn(eventList)
        .thenReturn(eventList);

    Mockito.doNothing()
        .when(this.eventProducer)
        .produce(ArgumentMatchers.anyString(), ArgumentMatchers.any());

    commandHandler.republishEvents();
    Mockito.verify(eventProducer, Mockito.times(1))
        .produce("UserRegisteredEvent", UserRegisteredEvent.builder().build());
  }
}
