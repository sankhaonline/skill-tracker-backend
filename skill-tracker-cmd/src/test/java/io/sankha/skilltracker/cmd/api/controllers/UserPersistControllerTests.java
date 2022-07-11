package io.sankha.skilltracker.cmd.api.controllers;

import io.sankha.skilltracker.cmd.api.commands.user.RegisterUserCommand;
import io.sankha.skilltracker.cmd.api.commands.user.UpdateUserCommand;
import io.sankha.skilltracker.cqrs.core.infrastructure.CommandDispatcher;
import io.sankha.skilltracker.exception.BusinessException;
import io.sankha.skilltracker.user.models.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserPersistControllerTests {

  @InjectMocks UserPersistController controller;

  @Mock private CommandDispatcher commandDispatcher;

  @Test
  void registerUserTest() {
    Mockito.doNothing().when(this.commandDispatcher).send(ArgumentMatchers.any());
    var responseDto =
        controller.createUser(
            RegisterUserCommand.builder().user(UserEntity.builder().build()).build());
    Assertions.assertEquals(HttpStatus.CREATED.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void registerUserExceptionTest() {
    Mockito.doThrow(new BusinessException("Unexpected value: "))
        .when(this.commandDispatcher)
        .send(ArgumentMatchers.any());
    var responseDto =
        controller.createUser(
            RegisterUserCommand.builder().user(UserEntity.builder().build()).build());
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void updateUserTest() {
    Mockito.doNothing().when(this.commandDispatcher).send(ArgumentMatchers.any());
    var responseDto =
        controller.updateUser(
            "", UpdateUserCommand.builder().user(UserEntity.builder().build()).build());
    Assertions.assertEquals(HttpStatus.OK.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void updateUserExceptionTest() {
    Mockito.doThrow(new RuntimeException("Unexpected value: "))
        .when(this.commandDispatcher)
        .send(ArgumentMatchers.any());
    var responseDto =
        controller.updateUser(
            "", UpdateUserCommand.builder().user(UserEntity.builder().build()).build());
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void removeUserTest() {
    Mockito.doNothing().when(this.commandDispatcher).send(ArgumentMatchers.any());
    var responseDto = controller.removeUser("");
    Assertions.assertEquals(HttpStatus.OK.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void removeUserExceptionTest() {
    Mockito.doThrow(new RuntimeException("Unexpected value: "))
        .when(this.commandDispatcher)
        .send(ArgumentMatchers.any());
    var responseDto = controller.removeUser("");
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }
}
