package io.sankha.skilltracker.cmd.api.controllers;

import io.sankha.skilltracker.cmd.api.commands.user.RegisterUserCommand;
import io.sankha.skilltracker.cmd.api.dto.RegisterUserResponse;
import io.sankha.skilltracker.cqrs.core.infrastructure.CommandDispatcher;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/signup")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RegisterUserController {
  private final Logger logger = Logger.getLogger(RegisterUserController.class.getName());

  private final CommandDispatcher commandDispatcher;

  @PostMapping("/registerUser")
  public ResponseEntity<RegisterUserResponse> registerUser(
      @RequestBody RegisterUserCommand command) {
    var id = UUID.randomUUID().toString();
    command.setId(id);

    try {
      commandDispatcher.send(command);

      return new ResponseEntity<>(
          new RegisterUserResponse(id, "User successfully registered!"), HttpStatus.CREATED);
    } catch (Exception e) {
      var safeErrorMessage = "Error while processing register user request for id - " + id;
      logger.log(Level.SEVERE, safeErrorMessage, e);

      return new ResponseEntity<>(
          new RegisterUserResponse(id, safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
