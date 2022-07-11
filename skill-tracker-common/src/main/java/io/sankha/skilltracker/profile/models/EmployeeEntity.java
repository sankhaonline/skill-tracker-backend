package io.sankha.skilltracker.profile.models;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class EmployeeEntity implements BaseEntity {
  @Id private String id;

  private String firstName;

  private String lastName;

  private String email;

  private String mobile;

  private int htmlCssJavascript;

  private int angular;

  private int react;

  private int spring;

  private int restful;

  private int hibernate;

  private int git;

  private int docker;

  private int jenkins;

  private int aws;

  private int spoken;

  private int communication;

  private int aptitude;
}
