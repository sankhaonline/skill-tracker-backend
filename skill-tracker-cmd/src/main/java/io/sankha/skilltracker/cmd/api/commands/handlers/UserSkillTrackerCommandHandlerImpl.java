package io.sankha.skilltracker.cmd.api.commands.handlers;

import io.sankha.skilltracker.cmd.api.commands.user.RegisterUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.RemoveUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.UpdateUserCommand;
import io.sankha.skilltracker.cmd.domain.UserAggregate;
import io.sankha.skilltracker.cqrs.core.handlers.EventSourcingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserSkillTrackerCommandHandlerImpl implements UserCommandHandler {
  private final EventSourcingHandler<UserAggregate> eventSourcingHandler;

  @Override
  public void handle(RegisterUserCommand command) {
    var aggregate = new UserAggregate(command);

    eventSourcingHandler.save(aggregate);
  }

  @Override
  public void handle(UpdateUserCommand command) {
    var aggregate = eventSourcingHandler.getById(command.getId());
    aggregate.updateUser(command);

    eventSourcingHandler.save((aggregate));
  }

  @Override
  public void handle(RemoveUserCommand command) {
    var aggregate = eventSourcingHandler.getById(command.getId());
    aggregate.removeUser(command);
    eventSourcingHandler.save((aggregate));
  }
}
