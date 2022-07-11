package io.sankha.skilltracker.cmd.api.controllers;

import io.sankha.skilltracker.cmd.api.commands.user.RegisterUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.RemoveUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.UpdateUserCommand;
import io.sankha.skilltracker.cmd.api.dto.RegisterUserResponse;
import io.sankha.skilltracker.common.dto.BaseResponse;
import io.sankha.skilltracker.cqrs.core.infrastructure.CommandDispatcher;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
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
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserPersistController {
  private final Logger logger = Logger.getLogger(UserPersistController.class.getName());

  private final CommandDispatcher commandDispatcher;

  @PostMapping("/createUser")
  @PreAuthorize("hasAuthority('ADMIN_PRIVILEGE')")
  public ResponseEntity<RegisterUserResponse> createUser(
      @Valid @RequestBody RegisterUserCommand command) {
    var id = UUID.randomUUID().toString();
    command.setId(id);

    try {
      commandDispatcher.send(command);

      return new ResponseEntity<>(
          new RegisterUserResponse(id, "User successfully created!"), HttpStatus.CREATED);
    } catch (Exception e) {
      var safeErrorMessage = "Error while processing create user request for id - " + id;
      logger.log(Level.SEVERE, safeErrorMessage, e);

      return new ResponseEntity<>(
          new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping(path = "/updateUser/{id}")
  @PreAuthorize("hasAuthority('ADMIN_PRIVILEGE')")
  public ResponseEntity<BaseResponse> updateUser(
      @PathVariable(value = "id") String id, @Valid @RequestBody UpdateUserCommand command) {
    try {
      command.setId(id);
      commandDispatcher.send(command);

      return new ResponseEntity<>(new BaseResponse("User successfully updated!"), HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Error while processing update user request for id - " + id;
      logger.log(Level.SEVERE, safeErrorMessage, e);

      return new ResponseEntity<>(
          new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping(path = "/removeUser/{id}")
  @PreAuthorize("hasAuthority('ADMIN_PRIVILEGE')")
  public ResponseEntity<BaseResponse> removeUser(@PathVariable(value = "id") String id) {
    try {
      commandDispatcher.send(new RemoveUserCommand(id));

      return new ResponseEntity<>(new BaseResponse("User successfully removed!"), HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Error while processing remove user request for id - " + id;
      logger.log(Level.SEVERE, safeErrorMessage, e);

      return new ResponseEntity<>(
          new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
