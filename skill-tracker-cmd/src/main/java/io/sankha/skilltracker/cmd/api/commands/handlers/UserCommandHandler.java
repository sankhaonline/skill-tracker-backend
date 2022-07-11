package io.sankha.skilltracker.cmd.api.commands.handlers;

import io.sankha.skilltracker.cmd.api.commands.user.RegisterUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.RemoveUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.UpdateUserCommand;

public interface UserCommandHandler {
  void handle(RegisterUserCommand command);

  void handle(UpdateUserCommand command);

  void handle(RemoveUserCommand command);
}
