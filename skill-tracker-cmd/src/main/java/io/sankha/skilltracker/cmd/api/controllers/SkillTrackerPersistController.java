package io.sankha.skilltracker.cmd.api.controllers;

import io.sankha.skilltracker.cmd.api.commands.skilltracker.DeleteEmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.EmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.UpdateEmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.dto.AddProfileResponse;
import io.sankha.skilltracker.common.dto.BaseResponse;
import io.sankha.skilltracker.cqrs.core.commands.BaseCommand;
import io.sankha.skilltracker.cqrs.core.infrastructure.CommandDispatcher;
import java.text.MessageFormat;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/skill-tracker/api/v1/engineer")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SkillTrackerPersistController {
  private final Logger logger = Logger.getLogger(SkillTrackerPersistController.class.getName());

  private final CommandDispatcher commandDispatcher;

  @PostMapping("/add-profile")
  @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
  public ResponseEntity<BaseResponse> addProfile(@RequestBody EmployeeProfileCommand command) {
    var id = UUID.randomUUID().toString();
    return persistProfile(id, command, commandDispatcher, logger);
  }

  @PutMapping("/update-profile/{id}")
  @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
  public ResponseEntity<BaseResponse> updateProfile(
      @PathVariable String id, @RequestBody UpdateEmployeeProfileCommand command) {
    return persistProfile(id, command, commandDispatcher, logger);
  }

  @DeleteMapping("/delete-profile/{id}")
  @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
  public ResponseEntity<BaseResponse> deleteProfile(@PathVariable String id) {
    var command = new DeleteEmployeeProfileCommand();
    command.setId(id);
    return persistProfile(id, command, commandDispatcher, logger);
  }

  static ResponseEntity<BaseResponse> persistProfile(
      @PathVariable String id,
      @RequestBody BaseCommand command,
      CommandDispatcher commandDispatcher,
      Logger logger) {
    try {
      command.setId(id);
      commandDispatcher.send(command);
      return new ResponseEntity<>(
          new AddProfileResponse("Profile creation request completed successfully!", id),
          HttpStatus.CREATED);
    } catch (IllegalStateException e) {
      logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}.", e));
      return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      var safeErrorMessage =
          MessageFormat.format(
              "Error while processing request to add a new profile for id - {0}.", id);
      logger.log(Level.SEVERE, safeErrorMessage, e);
      return new ResponseEntity<>(
          new AddProfileResponse(safeErrorMessage, id), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
