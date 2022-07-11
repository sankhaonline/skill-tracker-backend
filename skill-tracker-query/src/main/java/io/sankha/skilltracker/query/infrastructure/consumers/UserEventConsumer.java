package io.sankha.skilltracker.query.infrastructure.consumers;

import io.sankha.skilltracker.user.events.UserRegisteredEvent;
import io.sankha.skilltracker.user.events.UserRemovedEvent;
import io.sankha.skilltracker.user.events.UserUpdatedEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface UserEventConsumer {
  void consume(@Payload UserRegisteredEvent event, Acknowledgment ack);

  void consume(@Payload UserUpdatedEvent event, Acknowledgment ack);

  void consume(@Payload UserRemovedEvent event, Acknowledgment ack);
}
