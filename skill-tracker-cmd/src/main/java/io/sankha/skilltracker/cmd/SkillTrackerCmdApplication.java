package io.sankha.skilltracker.cmd;

import io.sankha.skilltracker.cmd.api.commands.handlers.SkillTrackerCommandHandler;
import io.sankha.skilltracker.cmd.api.commands.handlers.UserCommandHandler;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.DeleteEmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.EmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.RestoreReadDbCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.UpdateEmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.user.RegisterUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.RemoveUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.UpdateUserCommand;
import io.sankha.skilltracker.cqrs.core.infrastructure.CommandDispatcher;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SkillTrackerCmdApplication {

  private final CommandDispatcher commandDispatcher;
  private final SkillTrackerCommandHandler skillTrackerCommandHandler;
  private final UserCommandHandler userCommandHandler;

  public static void main(String... args) {
    SpringApplication.run(SkillTrackerCmdApplication.class, args);
  }

  @PostConstruct
  public void registerHandlers() {
    commandDispatcher.registerHandler(
        EmployeeProfileCommand.class, skillTrackerCommandHandler::handle);
    commandDispatcher.registerHandler(
        UpdateEmployeeProfileCommand.class, skillTrackerCommandHandler::handle);
    commandDispatcher.registerHandler(
        DeleteEmployeeProfileCommand.class, skillTrackerCommandHandler::handle);
    commandDispatcher.registerHandler(
        RestoreReadDbCommand.class, skillTrackerCommandHandler::handle);
    commandDispatcher.registerHandler(RegisterUserCommand.class, userCommandHandler::handle);
    commandDispatcher.registerHandler(UpdateUserCommand.class, userCommandHandler::handle);
    commandDispatcher.registerHandler(RemoveUserCommand.class, userCommandHandler::handle);
  }
}
