package io.sankha.skilltracker.cmd.api.controllers;

import io.sankha.skilltracker.cqrs.core.infrastructure.CommandDispatcher;
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
class RestoreReadDbControllerTests {

  @InjectMocks RestoreReadDbController controller;

  @Mock private CommandDispatcher commandDispatcher;

  @Test
  void restoreReadDbTest() {
    Mockito.doNothing().when(this.commandDispatcher).send(ArgumentMatchers.any());
    var responseDto = controller.restoreReadDb();
    Assertions.assertEquals(HttpStatus.OK.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void restoreReadDbIllegalStateExceptionTest() {
    Mockito.doThrow(new IllegalStateException("Unexpected value: "))
        .when(this.commandDispatcher)
        .send(ArgumentMatchers.any());
    var responseDto = controller.restoreReadDb();
    Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void restoreReadDbExceptionTest() {
    Mockito.doThrow(new RuntimeException("Unexpected value: "))
        .when(this.commandDispatcher)
        .send(ArgumentMatchers.any());
    var responseDto = controller.restoreReadDb();
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }
}
