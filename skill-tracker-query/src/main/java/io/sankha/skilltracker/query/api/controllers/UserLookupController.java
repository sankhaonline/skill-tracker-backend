package io.sankha.skilltracker.query.api.controllers;

import io.sankha.skilltracker.cqrs.core.infrastructure.QueryDispatcher;
import io.sankha.skilltracker.query.api.dto.UserInput;
import io.sankha.skilltracker.query.api.dto.UserLookupResponse;
import io.sankha.skilltracker.query.api.queries.user.FindAllUsersQuery;
import io.sankha.skilltracker.query.api.queries.user.FindUserByNameQuery;
import io.sankha.skilltracker.user.models.UserEntity;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserLookupController {
  private final Logger logger = Logger.getLogger(UserLookupController.class.getName());

  private final QueryDispatcher queryDispatcher;

  @GetMapping(path = "/")
  @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
  public ResponseEntity<UserLookupResponse> getAllUsers() {
    try {
      List<UserEntity> users = queryDispatcher.send(new FindAllUsersQuery());

      if (users.isEmpty()) {
        return new ResponseEntity<>(new UserLookupResponse(), HttpStatus.NO_CONTENT);
      }
      var response =
          UserLookupResponse.builder()
              .users(users)
              .message(MessageFormat.format("Successfully returned {0} User(s)!", users.size()))
              .build();
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Failed to complete get all users request";
      logger.info(e.toString());

      return new ResponseEntity<>(
          new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(path = "/name")
  @PreAuthorize(
      "hasAuthority('ADMIN_PRIVILEGE') or hasAuthority('WRITE_PRIVILEGE') or hasAuthority('READ_PRIVILEGE')")
  public ResponseEntity<UserLookupResponse> findUserByName(@RequestBody UserInput userInput) {
    try {
      List<UserEntity> users =
          queryDispatcher.send(new FindUserByNameQuery(userInput.getUserName()));

      if (users.isEmpty()) {
        return new ResponseEntity<>(new UserLookupResponse(), HttpStatus.NO_CONTENT);
      }

      var encoder = new BCryptPasswordEncoder(12);
      var userEntity = users.get(0);
      if (!encoder.matches(userInput.getPassword(), userEntity.getPassword())) {
        return new ResponseEntity<>(
            new UserLookupResponse("Credential mismatch"), HttpStatus.UNAUTHORIZED);
      }
      var response =
          UserLookupResponse.builder()
              .users(users)
              .message("Successfully returned User(s)!")
              .build();

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Failed to complete get user by ID request";
      logger.info(e.toString());

      return new ResponseEntity<>(
          new UserLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
