package io.sankha.skilltracker.user.events;

import io.sankha.skilltracker.cqrs.core.events.BaseEvent;
import io.sankha.skilltracker.user.models.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserRegisteredEvent extends BaseEvent {
  private UserEntity user;
}
