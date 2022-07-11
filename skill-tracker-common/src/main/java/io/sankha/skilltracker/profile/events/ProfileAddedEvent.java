package io.sankha.skilltracker.profile.events;

import io.sankha.skilltracker.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProfileAddedEvent extends BaseEvent {
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
