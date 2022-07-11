package io.sankha.skilltracker.query.infrastructure.handlers;

import io.sankha.skilltracker.profile.events.ProfileAddedEvent;
import io.sankha.skilltracker.profile.events.ProfileDeletedEvent;
import io.sankha.skilltracker.profile.events.ProfileUpdatedEvent;

public interface ProfileEventHandler {
  void on(ProfileAddedEvent event);

  void on(ProfileUpdatedEvent event);

  void on(ProfileDeletedEvent event);
}
