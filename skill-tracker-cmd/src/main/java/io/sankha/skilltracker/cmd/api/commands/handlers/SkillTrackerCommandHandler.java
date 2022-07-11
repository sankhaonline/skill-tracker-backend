package io.sankha.skilltracker.cmd.api.commands.handlers;

import io.sankha.skilltracker.cmd.api.commands.skilltracker.DeleteEmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.EmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.RestoreReadDbCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.UpdateEmployeeProfileCommand;

public interface SkillTrackerCommandHandler {
  void handle(EmployeeProfileCommand command);

  void handle(UpdateEmployeeProfileCommand command);

  void handle(DeleteEmployeeProfileCommand command);

  void handle(RestoreReadDbCommand command);
}
