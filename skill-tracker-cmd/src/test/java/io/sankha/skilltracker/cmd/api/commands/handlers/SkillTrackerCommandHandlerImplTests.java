package io.sankha.skilltracker.cmd.api.commands.handlers;

import io.sankha.skilltracker.cmd.api.commands.skilltracker.DeleteEmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.EmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.RestoreReadDbCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.UpdateEmployeeProfileCommand;
import io.sankha.skilltracker.cmd.domain.SkillTrackerAggregate;
import io.sankha.skilltracker.cqrs.core.handlers.EventSourcingHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SkillTrackerCommandHandlerImplTests {
  @InjectMocks SkillTrackerCommandHandlerImpl commandHandler;

  @Mock private EventSourcingHandler<SkillTrackerAggregate> eventSourcingHandler;

  @Test
  void handleEmployeeProfileCommandTest() {
    Mockito.doNothing().when(this.eventSourcingHandler).save(ArgumentMatchers.any());
    commandHandler.handle(new EmployeeProfileCommand());
    Mockito.verify(eventSourcingHandler, Mockito.times(1))
        .save(Mockito.any(SkillTrackerAggregate.class));
  }

  @Test
  void handleUpdateEmployeeProfileCommandTest() {
    Mockito.when(this.eventSourcingHandler.getById(ArgumentMatchers.any()))
        .thenReturn(new SkillTrackerAggregate());
    Mockito.doNothing().when(this.eventSourcingHandler).save(ArgumentMatchers.any());
    commandHandler.handle(new UpdateEmployeeProfileCommand());
    Mockito.verify(eventSourcingHandler, Mockito.times(1))
        .save(Mockito.any(SkillTrackerAggregate.class));
  }

  @Test
  void handleDeleteEmployeeProfileCommandTest() {
    Mockito.when(this.eventSourcingHandler.getById(ArgumentMatchers.any()))
        .thenReturn(new SkillTrackerAggregate());
    Mockito.doNothing().when(this.eventSourcingHandler).save(ArgumentMatchers.any());
    commandHandler.handle(new DeleteEmployeeProfileCommand());
    Mockito.verify(eventSourcingHandler, Mockito.times(1))
        .save(Mockito.any(SkillTrackerAggregate.class));
  }

  @Test
  void handleRestoreReadDbCommandTest() {
    Mockito.doNothing().when(this.eventSourcingHandler).republishEvents();
    commandHandler.handle(new RestoreReadDbCommand());
    Mockito.verify(eventSourcingHandler, Mockito.times(1)).republishEvents();
  }
}
