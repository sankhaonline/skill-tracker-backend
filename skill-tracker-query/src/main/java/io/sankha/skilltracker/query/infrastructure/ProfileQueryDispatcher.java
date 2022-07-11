package io.sankha.skilltracker.query.infrastructure;

import io.sankha.skilltracker.cqrs.core.domain.BaseEntity;
import io.sankha.skilltracker.cqrs.core.infrastructure.QueryDispatcher;
import io.sankha.skilltracker.cqrs.core.queries.BaseQuery;
import io.sankha.skilltracker.cqrs.core.queries.QueryHandlerMethod;
import io.sankha.skilltracker.exception.BusinessException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ProfileQueryDispatcher implements QueryDispatcher {
  private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

  @Override
  public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
    var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
    handlers.add(handler);
  }

  @Override
  public <U extends BaseEntity> List<U> send(BaseQuery query) {
    var handlers = routes.get(query.getClass());
    if (CollectionUtils.isEmpty(handlers)) {
      throw new BusinessException("No query handler was registered!");
    }
    if (handlers.size() > 1) {
      throw new BusinessException("Cannot send query to more than one handler!");
    }
    return handlers.get(0).handle(query);
  }
}
