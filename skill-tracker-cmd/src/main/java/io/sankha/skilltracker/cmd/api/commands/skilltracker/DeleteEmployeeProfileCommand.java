package io.sankha.skilltracker.cmd.api.commands.skilltracker;

import io.sankha.skilltracker.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class DeleteEmployeeProfileCommand extends BaseCommand {
  private String id;
}
