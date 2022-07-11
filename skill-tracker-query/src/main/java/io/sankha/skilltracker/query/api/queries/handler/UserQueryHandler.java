package io.sankha.skilltracker.query.api.queries.handler;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.query.api.queries.user.FindAllUsersQuery;
import io.sankha.skilltracker.query.api.queries.user.FindUserByIdQuery;
import io.sankha.skilltracker.query.api.queries.user.FindUserByNameQuery;
import java.util.List;

public interface UserQueryHandler {

  List<BaseEntity> handle(FindUserByIdQuery query);

  List<BaseEntity> handle(FindUserByNameQuery query);

  List<BaseEntity> handle(FindAllUsersQuery query);
}
