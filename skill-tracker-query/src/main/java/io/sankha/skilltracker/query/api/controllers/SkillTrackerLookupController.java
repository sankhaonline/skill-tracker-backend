package io.sankha.skilltracker.query.api.controllers;

import io.sankha.skilltracker.cqrs.core.infrastructure.QueryDispatcher;
import io.sankha.skilltracker.profile.models.EmployeeEntity;
import io.sankha.skilltracker.query.api.queries.profile.FindAllProfilesQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByIdQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileByNameQuery;
import io.sankha.skilltracker.query.api.queries.profile.FindProfileWithSkillQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/skill-tracker/api/v1/admin")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SkillTrackerLookupController {
  private final Logger logger = Logger.getLogger(SkillTrackerLookupController.class.getName());

  private final QueryDispatcher queryDispatcher;

  @GetMapping()
  @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
  public ResponseEntity<List<EmployeeEntity>> getAllEmployees() {
    try {
      List<EmployeeEntity> employeeEntities = queryDispatcher.send(new FindAllProfilesQuery());
      if (CollectionUtils.isEmpty(employeeEntities)) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(employeeEntities, HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Failed to complete get all employees request!";
      logger.log(Level.SEVERE, safeErrorMessage, e);
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{criteria}/{criteriaValue}")
  @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
  public ResponseEntity<List<EmployeeEntity>> getEmployeesByCriteria(
      @PathVariable String criteria, @PathVariable String criteriaValue) {
    List<EmployeeEntity> employeeEntities;
    try {
      switch (criteria) {
        case "Id":
          employeeEntities = queryDispatcher.send(new FindProfileByIdQuery(criteriaValue));
          break;
        case "Name":
          employeeEntities = queryDispatcher.send(new FindProfileByNameQuery(criteriaValue));
          break;
        case "Skill":
          employeeEntities = queryDispatcher.send(new FindProfileWithSkillQuery(criteriaValue));
          break;
        default:
          throw new IllegalStateException(String.format("Unexpected value: %s", criteria));
      }
      if (CollectionUtils.isEmpty(employeeEntities)) {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(employeeEntities, HttpStatus.OK);
    } catch (Exception e) {
      var safeErrorMessage = "Failed to complete get employees by holder request!";
      logger.log(Level.SEVERE, safeErrorMessage, e);
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
