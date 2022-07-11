package io.sankha.skilltracker.query.api.queries.handler;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.query.api.queries.user.FindAllUsersQuery;
import io.sankha.skilltracker.query.api.queries.user.FindUserByIdQuery;
import io.sankha.skilltracker.query.api.queries.user.FindUserByNameQuery;
import io.sankha.skilltracker.query.api.repositories.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserQueryHandlerImpl implements UserQueryHandler {

  private final UserRepository userRepository;

  @Override
  public List<BaseEntity> handle(FindAllUsersQuery query) {
    return StreamSupport.stream(userRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public List<BaseEntity> handle(FindUserByNameQuery query) {
    return new ArrayList<>(userRepository.findByUsername(String.valueOf(query.getUsername())));
  }

  @Override
  public List<BaseEntity> handle(FindUserByIdQuery query) {
    return userRepository.findById(String.valueOf(query.getId())).stream()
        .collect(Collectors.toList());
  }
}
