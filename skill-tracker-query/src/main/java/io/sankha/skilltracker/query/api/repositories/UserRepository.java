package io.sankha.skilltracker.query.api.repositories;

import io.sankha.skilltracker.user.models.UserEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {

  List<UserEntity> findByUsername(String username);
}
