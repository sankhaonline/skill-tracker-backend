package io.sankha.skilltracker.cmd.api.controllers;

import io.sankha.skilltracker.cmd.api.commands.skilltracker.RestoreReadDbCommand;
import io.sankha.skilltracker.common.dto.BaseResponse;
import io.sankha.skilltracker.cqrs.core.infrastructure.CommandDispatcher;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/restoreReadDb")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RestoreReadDbController {
  private final Logger logger = Logger.getLogger(RestoreReadDbController.class.getName());

  private final CommandDispatcher commandDispatcher;

  @PostMapping
  @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
  public ResponseEntity<BaseResponse> restoreReadDb() {
    try {
      commandDispatcher.send(new RestoreReadDbCommand());
      return new ResponseEntity<>(
          new BaseResponse("Read database restore request completed successfully!"), HttpStatus.OK);
    } catch (IllegalStateException e) {
      logger.log(Level.WARNING, MessageFormat.format("Client made a bad request - {0}.", e));
      return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      var safeErrorMessage = "Error while processing request to restore read database.";
      logger.log(Level.SEVERE, safeErrorMessage, e);
      return new ResponseEntity<>(
          new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
