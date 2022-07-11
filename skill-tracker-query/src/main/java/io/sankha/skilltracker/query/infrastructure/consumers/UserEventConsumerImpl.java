package io.sankha.skilltracker.query.infrastructure.consumers;

import io.sankha.skilltracker.query.infrastructure.handlers.UserEventHandler;
import io.sankha.skilltracker.user.events.UserRegisteredEvent;
import io.sankha.skilltracker.user.events.UserRemovedEvent;
import io.sankha.skilltracker.user.events.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserEventConsumerImpl implements UserEventConsumer {
  private final UserEventHandler userEventHandler;

  @KafkaListener(topics = "UserRegisteredEvent", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(@Payload UserRegisteredEvent event, Acknowledgment ack) {
    this.userEventHandler.on(event);
    ack.acknowledge();
  }

  @KafkaListener(topics = "UserUpdatedEvent", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(@Payload UserUpdatedEvent event, Acknowledgment ack) {
    this.userEventHandler.on(event);
    ack.acknowledge();
  }

  @KafkaListener(topics = "UserRemovedEvent", groupId = "${spring.kafka.consumer.group-id}")
  @Override
  public void consume(@Payload UserRemovedEvent event, Acknowledgment ack) {
    this.userEventHandler.on(event);
    ack.acknowledge();
  }
}
