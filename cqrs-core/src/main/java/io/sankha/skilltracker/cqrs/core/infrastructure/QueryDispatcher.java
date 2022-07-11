package io.sankha.skilltracker.cqrs.core.infrastructure;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.cqrs.core.queries.BaseQuery;
import io.sankha.skilltracker.cqrs.core.queries.QueryHandlerMethod;
import java.util.List;

public interface QueryDispatcher {
  <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);

  <U extends BaseEntity> List<U> send(BaseQuery query);
}
