package io.sankha.skilltracker.cmd.infrastructure;

import io.sankha.skilltracker.cmd.api.commands.handlers.SkillTrackerCommandHandler;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.EmployeeProfileCommand;
import io.sankha.skilltracker.cqrs.core.commands.BaseCommand;
import io.sankha.skilltracker.cqrs.core.commands.CommandHandlerMethod;
import io.sankha.skilltracker.exception.BusinessException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SkillTrackerCommandDispatcherTests {

  @InjectMocks SkillTrackerCommandDispatcher commandDispatcher;

  @Mock Map<Class<? extends BaseCommand>, List<CommandHandlerMethod<? extends BaseCommand>>> routes;

  @Mock SkillTrackerCommandHandler skillTrackerCommandHandler;

  @BeforeEach
  void init(TestInfo info) {
    if (info.getDisplayName().equals("sendExceptionTest()")) {
      return;
    }
    if (info.getDisplayName().equals("sendException2Test()")) {
      commandDispatcher.registerHandler(
          EmployeeProfileCommand.class, skillTrackerCommandHandler::handle);
    }
    commandDispatcher.registerHandler(
        EmployeeProfileCommand.class, skillTrackerCommandHandler::handle);
  }

  @Test
  void registerHandlerTest() {
    Mockito.when(this.routes.computeIfAbsent(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(new LinkedList<>());
    commandDispatcher.registerHandler(
        EmployeeProfileCommand.class, skillTrackerCommandHandler::handle);
    Mockito.verify(routes, Mockito.times(0))
        .computeIfAbsent(EmployeeProfileCommand.class, c -> new LinkedList<>());
  }

  @Test
  void sendTest() {
    Mockito.when(this.routes.get(ArgumentMatchers.any())).thenReturn(new LinkedList<>());

    commandDispatcher.send(new EmployeeProfileCommand());
    Mockito.verify(routes, Mockito.times(0)).get(EmployeeProfileCommand.class);
  }

  @Test
  void sendExceptionTest() {
    Mockito.when(this.routes.get(ArgumentMatchers.any())).thenReturn(new LinkedList<>());

    var command = new EmployeeProfileCommand();
    BusinessException thrown =
        Assertions.assertThrows(BusinessException.class, () -> commandDispatcher.send(command));

    Assertions.assertTrue(thrown.getMessage().contains("No command handler was registered!"));
  }

  @Test
  void sendException2Test() {
    Mockito.when(this.routes.get(ArgumentMatchers.any())).thenReturn(new LinkedList<>());

    var command = new EmployeeProfileCommand();
    BusinessException thrown =
        Assertions.assertThrows(BusinessException.class, () -> commandDispatcher.send(command));

    Assertions.assertTrue(
        thrown.getMessage().contains("Cannot send command to more than one handler!"));
  }
}
