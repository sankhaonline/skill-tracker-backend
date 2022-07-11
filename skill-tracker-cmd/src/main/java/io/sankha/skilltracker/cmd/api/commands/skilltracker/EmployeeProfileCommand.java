package io.sankha.skilltracker.cmd.api.commands.skilltracker;

import io.sankha.skilltracker.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class EmployeeProfileCommand extends BaseCommand {
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
