package io.sankha.skilltracker.query.api.controllers;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.cqrs.core.infrastructure.QueryDispatcher;
import io.sankha.skilltracker.query.api.dto.UserInput;
import io.sankha.skilltracker.user.models.UserEntity;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserLookupControllerTests {

  @InjectMocks UserLookupController controller;

  @Mock private QueryDispatcher queryDispatcher;

  @Mock List<BaseEntity> mockList;

  @Test
  void getAllUsersTest() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any())).thenReturn(mockList);

    var responseDto = this.controller.getAllUsers();
    Assertions.assertEquals(HttpStatus.OK.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getAllUsersEmptyTest() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenReturn(Collections.emptyList());

    var responseDto = this.controller.getAllUsers();
    Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getAllUsersExceptionTest() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenThrow(new IllegalStateException("Unexpected value: "));

    var responseDto = this.controller.getAllUsers();
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void findUserByNameTest() {

    var userEntity = UserEntity.builder().username("test").password("test").build();

    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any())).thenReturn(mockList);
    Mockito.when(mockList.get(ArgumentMatchers.anyInt())).thenReturn(userEntity);

    try (var mocked = Mockito.mockConstruction(BCryptPasswordEncoder.class)) {
      var encoder = new BCryptPasswordEncoder(12);
      Mockito.when(encoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
          .thenReturn(true);
    }

    var userInput = new UserInput();
    userInput.setPassword("test");

    var responseDto = this.controller.findUserByName(userInput);

    Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getEmployeesByCriteriaEmptyTest() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenReturn(Collections.emptyList());

    var responseDto = this.controller.findUserByName(new UserInput());
    Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getEmployeesByCriteriaExceptionTest() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenThrow(new IllegalStateException("Unexpected value: "));

    var responseDto = this.controller.findUserByName(new UserInput());
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }
}
