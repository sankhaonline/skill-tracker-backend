package io.sankha.skilltracker.cmd.domain;

import io.sankha.skilltracker.cmd.api.commands.skilltracker.DeleteEmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.EmployeeProfileCommand;
import io.sankha.skilltracker.cmd.api.commands.skilltracker.UpdateEmployeeProfileCommand;
import io.sankha.skilltracker.cqrs.core.domain.AggregateRoot;
import io.sankha.skilltracker.profile.events.ProfileAddedEvent;
import io.sankha.skilltracker.profile.events.ProfileDeletedEvent;
import io.sankha.skilltracker.profile.events.ProfileUpdatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SkillTrackerAggregate extends AggregateRoot {
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

  public SkillTrackerAggregate(EmployeeProfileCommand command) {
    raiseEvent(
        ProfileAddedEvent.builder()
            .id(command.getId())
            .firstName(command.getFirstName())
            .lastName(command.getLastName())
            .email(command.getEmail())
            .mobile(command.getMobile())
            .angular(command.getAngular())
            .aptitude(command.getAptitude())
            .aws(command.getAws())
            .communication(command.getCommunication())
            .docker(command.getDocker())
            .git(command.getGit())
            .hibernate(command.getHibernate())
            .htmlCssJavascript(command.getHtmlCssJavascript())
            .jenkins(command.getJenkins())
            .react(command.getReact())
            .restful(command.getRestful())
            .spoken(command.getSpoken())
            .spring(command.getSpring())
            .build());
  }

  public void apply(ProfileAddedEvent event) {
    this.id = event.getId();
    this.firstName = event.getFirstName();
    this.lastName = event.getLastName();
    this.email = event.getEmail();
    this.mobile = event.getMobile();
    this.angular = event.getAngular();
    this.aptitude = event.getAptitude();
    this.aws = event.getAws();
    this.communication = event.getCommunication();
    this.docker = event.getDocker();
    this.git = event.getGit();
    this.hibernate = event.getHibernate();
    this.htmlCssJavascript = event.getHtmlCssJavascript();
    this.jenkins = event.getJenkins();
    this.react = event.getReact();
    this.restful = event.getRestful();
    this.spoken = event.getSpoken();
    this.spring = event.getSpring();
  }

  public void updateEmployee(UpdateEmployeeProfileCommand command) {
    raiseEvent(
        ProfileUpdatedEvent.builder()
            .id(command.getId())
            .firstName(command.getFirstName())
            .lastName(command.getLastName())
            .email(command.getEmail())
            .mobile(command.getMobile())
            .angular(command.getAngular())
            .aptitude(command.getAptitude())
            .aws(command.getAws())
            .communication(command.getCommunication())
            .docker(command.getDocker())
            .hibernate(command.getHibernate())
            .jenkins(command.getJenkins())
            .git(command.getGit())
            .htmlCssJavascript(command.getHtmlCssJavascript())
            .react(command.getReact())
            .restful(command.getRestful())
            .spoken(command.getSpoken())
            .spring(command.getSpring())
            .build());
  }

  public void apply(ProfileUpdatedEvent event) {
    this.id = event.getId();
    this.firstName = event.getFirstName();
    this.lastName = event.getLastName();
    this.email = event.getEmail();
    this.mobile = event.getMobile();
    this.angular = event.getAngular();
    this.aptitude = event.getAptitude();
    this.aws = event.getAws();
    this.communication = event.getCommunication();
    this.docker = event.getDocker();
    this.git = event.getGit();
    this.hibernate = event.getHibernate();
    this.htmlCssJavascript = event.getHtmlCssJavascript();
    this.jenkins = event.getJenkins();
    this.react = event.getReact();
    this.restful = event.getRestful();
    this.spoken = event.getSpoken();
    this.spring = event.getSpring();
  }

  public void deleteEmployee(DeleteEmployeeProfileCommand command) {
    raiseEvent(ProfileDeletedEvent.builder().id(command.getId()).build());
  }

  public void apply(ProfileDeletedEvent event) {
    this.id = event.getId();
    this.firstName = null;
    this.lastName = null;
    this.email = null;
    this.mobile = null;
    this.angular = 0;
    this.aptitude = 0;
    this.aws = 0;
    this.communication = 0;
    this.docker = 0;
    this.git = 0;
    this.hibernate = 0;
    this.htmlCssJavascript = 0;
    this.jenkins = 0;
    this.react = 0;
    this.restful = 0;
    this.spoken = 0;
    this.spring = 0;
  }
}
