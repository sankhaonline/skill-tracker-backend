package io.sankha.skilltracker.query.infrastructure.handlers;

import io.sankha.skilltracker.user.events.UserRegisteredEvent;
import io.sankha.skilltracker.user.events.UserRemovedEvent;
import io.sankha.skilltracker.user.events.UserUpdatedEvent;

public interface UserEventHandler {
  void on(UserRegisteredEvent event);

  void on(UserUpdatedEvent event);

  void on(UserRemovedEvent event);
}
