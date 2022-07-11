package io.sankha.user.oauth2.services;

import io.sankha.skilltracker.cqrs.core.events.EventModel;
import io.sankha.skilltracker.user.events.UserRegisteredEvent;
import io.sankha.user.oauth2.repositories.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements UserDetailsService {

  private final AuthUserRepository authUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user =
        authUserRepository.findByUsername(username).stream()
            .map(EventModel::getEventData)
            .map(baseEvent -> ((UserRegisteredEvent) baseEvent).getUser())
            .findAny()
            .orElseThrow(
                () -> new UsernameNotFoundException("Incorrect Username / Password supplied!"));

    return org.springframework.security.core.userdetails.User.withUsername(username)
        .password(user.getPassword())
        .authorities(user.getRoles())
        .accountExpired(false)
        .accountLocked(false)
        .credentialsExpired(false)
        .disabled(false)
        .build();
  }
}
