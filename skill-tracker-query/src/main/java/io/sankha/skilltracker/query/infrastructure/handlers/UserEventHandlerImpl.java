package io.sankha.skilltracker.query.infrastructure.handlers;

import io.sankha.skilltracker.exception.BusinessException;
import io.sankha.skilltracker.query.api.repositories.UserRepository;
import io.sankha.skilltracker.user.events.UserRegisteredEvent;
import io.sankha.skilltracker.user.events.UserRemovedEvent;
import io.sankha.skilltracker.user.events.UserUpdatedEvent;
import io.sankha.skilltracker.user.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserEventHandlerImpl implements UserEventHandler {

  private final UserRepository userRepository;

  @Override
  public void on(UserRegisteredEvent event) {
    var eventUser = event.getUser();
    var userEntity =
        UserEntity.builder()
            .id(event.getId())
            .firstname(eventUser.getFirstname())
            .lastname(eventUser.getLastname())
            .emailAddress(eventUser.getEmailAddress())
            .username(eventUser.getUsername())
            .password(eventUser.getPassword())
            .roles(eventUser.getRoles())
            .build();
    userRepository.save(userEntity);
  }

  @Override
  public void on(UserUpdatedEvent event) {
    var currentUser = userRepository.findById(event.getId()).orElseThrow(BusinessException::new);

    var eventUser = event.getUser();

    currentUser.setFirstname(eventUser.getFirstname());
    currentUser.setLastname(eventUser.getLastname());
    currentUser.setEmailAddress(eventUser.getEmailAddress());
    currentUser.setUsername(eventUser.getUsername());
    currentUser.setPassword(eventUser.getPassword());
    currentUser.setRoles(eventUser.getRoles());
    userRepository.save(currentUser);
  }

  @Override
  public void on(UserRemovedEvent event) {
    userRepository.deleteById(event.getId());
  }
}
