package io.sankha.skilltracker.cmd.api.commands.user;

import io.sankha.skilltracker.cqrs.core.commands.BaseCommand;
import io.sankha.skilltracker.user.models.UserEntity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserCommand extends BaseCommand {
  private String id;

  @NotNull(message = "no user details were supplied")
  @Valid
  private UserEntity user;
}
