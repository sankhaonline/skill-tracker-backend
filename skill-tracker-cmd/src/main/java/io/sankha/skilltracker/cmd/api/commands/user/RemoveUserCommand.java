package io.sankha.skilltracker.cmd.api.commands.user;

import io.sankha.skilltracker.cqrs.core.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveUserCommand extends BaseCommand {
  private String id;
}
