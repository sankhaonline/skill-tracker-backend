package io.sankha.skilltracker.query.api.controllers;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.cqrs.core.infrastructure.QueryDispatcher;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class SkillTrackerLookupControllerTests {

  @InjectMocks SkillTrackerLookupController controller;

  @Mock private QueryDispatcher queryDispatcher;

  @Mock List<BaseEntity> mockList;

  @Test
  void getAllEmployeesTest() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any())).thenReturn(mockList);

    var responseDto = this.controller.getAllEmployees();
    Assertions.assertEquals(HttpStatus.OK.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getAllEmployeesEmptyTest() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenReturn(Collections.emptyList());

    var responseDto = this.controller.getAllEmployees();
    Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getAllEmployeesExceptionTest() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenThrow(new IllegalStateException("Unexpected value: "));

    var responseDto = this.controller.getAllEmployees();
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getEmployeesByCriteria() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any())).thenReturn(mockList);

    var responseDtoId = this.controller.getEmployeesByCriteria("Id", "123");
    Assertions.assertNotNull(responseDtoId);

    var responseDtoName = this.controller.getEmployeesByCriteria("Name", "123");
    Assertions.assertNotNull(responseDtoName);

    var responseDtoSkill = this.controller.getEmployeesByCriteria("Skill", "123");
    Assertions.assertNotNull(responseDtoSkill);
  }

  @Test
  void getEmployeesByCriteriaEmpty() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenReturn(Collections.emptyList());

    var responseDto = this.controller.getEmployeesByCriteria("Id", "123");
    Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getEmployeesByCriteriaDefault() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenReturn(Collections.emptyList());

    var responseDto = this.controller.getEmployeesByCriteria("", "");
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }

  @Test
  void getEmployeesByCriteriaException() {
    Mockito.when(this.queryDispatcher.send(ArgumentMatchers.any()))
        .thenThrow(new IllegalStateException("Unexpected value: "));

    var responseDto = this.controller.getEmployeesByCriteria("", "");
    Assertions.assertEquals(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), responseDto.getStatusCodeValue());
  }
}
