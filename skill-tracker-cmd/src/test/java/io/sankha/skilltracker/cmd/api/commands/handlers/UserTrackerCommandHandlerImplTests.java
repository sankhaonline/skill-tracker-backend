package io.sankha.skilltracker.cmd.api.commands.handlers;

import io.sankha.skilltracker.cmd.api.commands.user.RegisterUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.RemoveUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.UpdateUserCommand;
import io.sankha.skilltracker.cmd.domain.UserAggregate;
import io.sankha.skilltracker.cqrs.core.handlers.EventSourcingHandler;
import io.sankha.skilltracker.user.models.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserTrackerCommandHandlerImplTests {
  @InjectMocks UserSkillTrackerCommandHandlerImpl commandHandler;

  @Mock private EventSourcingHandler<UserAggregate> eventSourcingHandler;

  @Test
  void handleRegisterUserCommandTest() {
    Mockito.doNothing().when(this.eventSourcingHandler).save(ArgumentMatchers.any());
    commandHandler.handle(
        RegisterUserCommand.builder().user(UserEntity.builder().password("").build()).build());
    Mockito.verify(eventSourcingHandler, Mockito.times(1)).save(Mockito.any(UserAggregate.class));
  }

  @Test
  void handleUpdateUserCommandTest() {
    Mockito.when(this.eventSourcingHandler.getById(ArgumentMatchers.any()))
        .thenReturn(new UserAggregate());
    Mockito.doNothing().when(this.eventSourcingHandler).save(ArgumentMatchers.any());
    commandHandler.handle(
        UpdateUserCommand.builder().user(UserEntity.builder().password("").build()).build());
    Mockito.verify(eventSourcingHandler, Mockito.times(1)).save(Mockito.any(UserAggregate.class));
  }

  @Test
  void handleRemoveUserCommandTest() {
    Mockito.when(this.eventSourcingHandler.getById(ArgumentMatchers.any()))
        .thenReturn(new UserAggregate());
    Mockito.doNothing().when(this.eventSourcingHandler).save(ArgumentMatchers.any());
    commandHandler.handle(new RemoveUserCommand(""));
    Mockito.verify(eventSourcingHandler, Mockito.times(1)).save(Mockito.any(UserAggregate.class));
  }
}
