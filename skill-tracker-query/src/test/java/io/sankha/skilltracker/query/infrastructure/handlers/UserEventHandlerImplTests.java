package io.sankha.skilltracker.query.infrastructure.handlers;

import io.sankha.skilltracker.query.api.repositories.UserRepository;
import io.sankha.skilltracker.user.events.UserRegisteredEvent;
import io.sankha.skilltracker.user.events.UserRemovedEvent;
import io.sankha.skilltracker.user.events.UserUpdatedEvent;
import io.sankha.skilltracker.user.models.UserEntity;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserEventHandlerImplTests {

  @InjectMocks UserEventHandlerImpl eventHandler;

  @Mock private UserRepository repository;

  @Test
  void onUserRegisteredEventTest() {
    Mockito.when(this.repository.save(ArgumentMatchers.any()))
        .thenReturn(Mockito.any(UserEntity.class));
    eventHandler.on(UserRegisteredEvent.builder().user(UserEntity.builder().build()).build());
    Mockito.verify(repository, Mockito.times(1)).save(UserEntity.builder().build());
  }

  @Test
  void onUserUpdatedEventTest() {
    UserEntity entity = Mockito.mock(UserEntity.class);
    Mockito.when(this.repository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.ofNullable(entity));

    Mockito.when(this.repository.save(ArgumentMatchers.any()))
        .thenReturn(Mockito.any(UserEntity.class));
    eventHandler.on(UserUpdatedEvent.builder().user(UserEntity.builder().build()).build());
    Mockito.verify(repository, Mockito.times(1)).findById(null);
    Mockito.verify(repository, Mockito.times(1)).save(entity);
  }

  @Test
  void onUserRemovedEventTest() {
    Mockito.doNothing().when(this.repository).deleteById(ArgumentMatchers.any());
    eventHandler.on(UserRemovedEvent.builder().build());
    Mockito.verify(repository, Mockito.times(1)).deleteById(null);
  }
}
