package io.sankha.skilltracker.query.infrastructure.handlers;

import io.sankha.skilltracker.profile.events.ProfileAddedEvent;
import io.sankha.skilltracker.profile.events.ProfileUpdatedEvent;
import io.sankha.skilltracker.profile.models.EmployeeEntity;
import io.sankha.skilltracker.query.api.repositories.ProfileRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProfileEventHandlerImplTests {

  @InjectMocks ProfileEventHandlerImpl eventHandler;

  @Mock private ProfileRepository repository;

  @Test
  void onProfileAddedEventTest() {
    Mockito.when(this.repository.save(ArgumentMatchers.any()))
        .thenReturn(Mockito.any(EmployeeEntity.class));
    eventHandler.on(ProfileAddedEvent.builder().build());
    Mockito.verify(repository, Mockito.times(1)).save(EmployeeEntity.builder().build());
  }

  @Test
  void onProfileUpdatedEventTest() {
    EmployeeEntity entity = Mockito.mock(EmployeeEntity.class);
    Mockito.when(this.repository.findById(ArgumentMatchers.any()))
        .thenReturn(Optional.ofNullable(entity));

    Mockito.when(this.repository.save(ArgumentMatchers.any()))
        .thenReturn(Mockito.any(EmployeeEntity.class));
    eventHandler.on(ProfileUpdatedEvent.builder().build());
    Mockito.verify(repository, Mockito.times(1)).findById(null);
    Mockito.verify(repository, Mockito.times(1)).save(entity);
  }
}
