package io.sankha.skilltracker.query.infrastructure.consumers;

import io.sankha.skilltracker.profile.events.ProfileAddedEvent;
import io.sankha.skilltracker.profile.events.ProfileDeletedEvent;
import io.sankha.skilltracker.profile.events.ProfileUpdatedEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface ProfileEventConsumer {
  void consume(@Payload ProfileUpdatedEvent event, Acknowledgment ack);

  void consume(@Payload ProfileAddedEvent event, Acknowledgment ack);

  void consume(@Payload ProfileDeletedEvent event, Acknowledgment ack);
}
